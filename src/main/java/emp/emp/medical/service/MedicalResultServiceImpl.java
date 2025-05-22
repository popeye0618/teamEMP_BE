package emp.emp.medical.service;

import emp.emp.auth.custom.CustomUserDetails;
import emp.emp.calendar.entity.CalendarEvent;
import emp.emp.calendar.enums.CalendarEventType;
import emp.emp.calendar.repository.CalendarRepository;
import emp.emp.common.dto.ImageDto;
import emp.emp.common.entity.Image;
import emp.emp.common.service.ImageService;
import emp.emp.exception.BusinessException;
import emp.emp.medical.dto.request.MedicalResultRequest;
import emp.emp.medical.dto.response.MedicalResultResponse;
import emp.emp.medical.entity.MedicalResult;
import emp.emp.medical.exception.MedicalResultErrorCode;
import emp.emp.medical.repository.MedicalResultRepository;
import emp.emp.member.entity.Member;
import emp.emp.member.repository.MemberRepository;
import emp.emp.util.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MedicalResultServiceImpl implements MedicalResultService {

  private final SecurityUtil securityUtil;
  private final CalendarRepository calendarRepository;
  private final MedicalResultRepository medicalResultRepository;
  private final ImageService imageService;


  /**
   * 진료 결과 등록
   * @param userDetails 인증된 사용자의 정보
   * @param eventId 캘린더 이벤트 시퀀스 Id
   * @param request 진료결과 등록 요청 데이터
   * @return
   */
  @Override
  @Transactional
  public MedicalResultResponse createMedicalResult(CustomUserDetails userDetails, Long eventId, MedicalResultRequest request){
    try{
      // 현재 로그인한 회원의 정보 가져옴
      Member currentMember = securityUtil.getCurrentMember();

      // 캘린더 이벤트 조회 & 소유권 검증
      CalendarEvent calendarEvent = findEventByIdAndValidate(eventId, currentMember);

      // 이벤트 타입이 진료결과 인지 확인
      validateEventType(calendarEvent);

      // 이미 진료 결과가 존재하는지 확인
      medicalResultRepository.findByCalendarEvent(calendarEvent)
              .ifPresent(result -> {
                throw new BusinessException(MedicalResultErrorCode.MEDICAL_RESULT_ALREADY_EXISTS);
              });

      // 이미지 엔티티 조회
      Image prescriptionImage = null;
      Image medicineImage = null;

      // 처방전 이미지 ID가 있으면 -> 해당 이미지 조회
      if (request.getPrescriptionImageId() != null) {
        prescriptionImage = imageService.getImageEntity(request.getPrescriptionImageId());
      }

      // 약 이미지 ID가 있으면 -> 해당 이미지 조회
      if (request.getMedicineImageId() != null) {
        medicineImage = imageService.getImageEntity(request.getMedicineImageId());
      }

      // 진료 결과 Entity 생성
      MedicalResult medicalResult = MedicalResult.builder()
              .calendarEvent(calendarEvent)
              .member(currentMember)
              .memo(request.getMemo())
              .prescriptionImage(prescriptionImage)
              .medicineImage(medicineImage)
              .isPublic(request.isPublic())
              .build();

      // 진료 결과 저장
      medicalResultRepository.save(medicalResult);

      // 응답 DTO로 변환하여 반환
      return convertToDto(medicalResult);

    }catch(BusinessException e){
      throw e;
    }catch(Exception e){
      throw new BusinessException(MedicalResultErrorCode.DATABASE_ERROR);
    }
  }

  /**
   * 진료 결과( medical_result) 조회
   * @param userDetails 인증된 사용자의 정보
   * @param eventId  캘린더 이벤트 시퀀스 Id
   * @return
   */
  @Override
  @Transactional(readOnly = true)
  public MedicalResultResponse getMedicalResult(CustomUserDetails userDetails, Long eventId) {
      try{
        Member currentMember = securityUtil.getCurrentMember();

        CalendarEvent calendarEvent = findEventByIdAndValidate(eventId, currentMember);

        validateEventType(calendarEvent);

        // 진료 결과 조회
        MedicalResult medicalResult = medicalResultRepository.findByCalendarEvent(calendarEvent)
                .orElseThrow(() -> new BusinessException(MedicalResultErrorCode.MEDICAL_RESULT_NOT_FOUND));

        return convertToDto(medicalResult);

      } catch(BusinessException e){
        throw e;
      }catch(Exception e){
        throw new BusinessException(MedicalResultErrorCode.DATABASE_ERROR);
      }


  }


  // =====================================================================

  /**
   * 캘린더 이벤트의 시퀀스ID로 이벤트 조회 & 현재 사용자의 소유인지 검증
   * @param eventId
   * @param currentMember
   * @return 검증된 캘린더 이벤트
   */
  private CalendarEvent findEventByIdAndValidate(Long eventId, Member currentMember){
    // 캘린더 이벤트 조회
    CalendarEvent calendarEvent = calendarRepository.findById(eventId)
            .orElseThrow(() -> new BusinessException(MedicalResultErrorCode.CALENDAR_EVENT_NOT_FOUND));

    // 로그인한 사용자의 일정인지 소유권 확인
    if(!calendarEvent.getMember().equals(currentMember)){
      throw new BusinessException(MedicalResultErrorCode.ACCESS_DENIED);
    }
    return calendarEvent;
  }

  /**
   * 이벤트 타입이 CHECKUP(진료 결과)인지 확인!
   * @param calendarEvent 확인할 캘린더 이벤트
   */
  private void validateEventType(CalendarEvent calendarEvent){
    if(calendarEvent.getEventType() != CalendarEventType.CHECKUP){
      throw new BusinessException(MedicalResultErrorCode.EVENT_TYPE_INVALID);
    }
  }


  /**
   * MedicalResult 엔티티를 MedicalResultResponse DTO로 변환
   * @param medicalResult 변환할 진료 결과 엔티티
   * @return 변환된 DTO
   */
  private MedicalResultResponse convertToDto(MedicalResult medicalResult) {
    // 캘린더 이벤트 정보 가져오기
    CalendarEvent calendarEvent = medicalResult.getCalendarEvent();

    // 처방전 이미지 정보 변환
    ImageDto prescriptionImageDto = null;
    if (medicalResult.getPrescriptionImage() != null) {
      Image image = medicalResult.getPrescriptionImage();
      prescriptionImageDto = ImageDto.builder()
              .imageId(image.getImageId())
              .fileName(image.getFileName())
              .filePath(image.getFilePath())
              .fileSize(image.getFileSize())
              .contentType(image.getContentType())
              .build();
    }

    // 약 이미지 정보 변환
    ImageDto medicineImageDto = null;
    if (medicalResult.getMedicineImage() != null) {
      Image image = medicalResult.getMedicineImage();
      medicineImageDto = ImageDto.builder()
              .imageId(image.getImageId())
              .fileName(image.getFileName())
              .filePath(image.getFilePath())
              .fileSize(image.getFileSize())
              .contentType(image.getContentType())
              .build();
    }

    // 진료 결과 응답 DTO 생성 & 반환
    return MedicalResultResponse.builder()
            .resultId(medicalResult.getResultId())
            .eventId(calendarEvent.getEventId())
            .verifyId(calendarEvent.getMember().getVerifyId())
            .title(calendarEvent.getTitle())
            .startDate(calendarEvent.getStartDate())
            .endDate(calendarEvent.getEndDate())
            .memo(medicalResult.getMemo())
            .prescriptionImage(prescriptionImageDto)
            .medicineImage(medicineImageDto)
            .isPublic(medicalResult.isPublic())
            .build();
  }


}
