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
import emp.emp.medical.dto.response.FamilyMedicalResultResponse;
import emp.emp.medical.dto.response.MedicalResultResponse;
import emp.emp.medical.entity.MedicalResult;
import emp.emp.medical.exception.MedicalResultErrorCode;
import emp.emp.medical.repository.MedicalResultRepository;
import emp.emp.member.entity.Member;
import emp.emp.member.repository.MemberRepository;
import emp.emp.util.security.SecurityUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MedicalResultServiceImpl implements MedicalResultService {

  private final SecurityUtil securityUtil;
  private final CalendarRepository calendarRepository;
  private final MedicalResultRepository medicalResultRepository;
  private final ImageService imageService;

  @PersistenceContext
  private EntityManager entityManager;


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

  /**
   * 진료결과 수정
   * @param userDetails 인증된 사용자의 정보
   * @param eventId 캘린더 이벤트 시퀀스 Id
   * @param request 진료 결과 수정 요청 데이터
   * @return
   */
  @Override
  @Transactional
  public MedicalResultResponse updateMedicalResult(CustomUserDetails userDetails, Long eventId, MedicalResultRequest request){

    try{
      Member currentMember = securityUtil.getCurrentMember();

      CalendarEvent calendarEvent = findEventByIdAndValidate(eventId, currentMember);

      validateEventType(calendarEvent);

      MedicalResult medicalResult = medicalResultRepository.findByCalendarEvent(calendarEvent)
              .orElseThrow(() -> new BusinessException(MedicalResultErrorCode.MEDICAL_RESULT_NOT_FOUND));

      // 메모 업데이트
      medicalResult.setMemo(request.getMemo());

      // 공개 여부 업데이트
      medicalResult.setPublic(request.isPublic());

      // 처방전 이미지 업데아트
      if(request.getPrescriptionImageId() != null){
        Image prescriptionImage = imageService.getImageEntity(request.getPrescriptionImageId());
        medicalResult.setPrescriptionImage(prescriptionImage);
      } else{
        medicalResult.setPrescriptionImage(null);
      }

      // 약 이미지 업데이트
      if(request.getMedicineImageId() != null){
        Image medicineImage = imageService.getImageEntity(request.getMedicineImageId());
        medicalResult.setMedicineImage(medicineImage);
      }else{
        medicalResult.setMedicineImage(null);
      }

      return convertToDto(medicalResult);

    }catch(BusinessException e){
      throw e;
    } catch(Exception e){
      throw new BusinessException(MedicalResultErrorCode.DATABASE_ERROR);
    }
  }

  /**
   * 진료 결과 삭제
   * @param userDetails 인증된 사용자의 정보
   * @param eventId 캘린터 이벤트 시퀀스 ID
   */
  @Override
  @Transactional
  public void deleteMedicalResult(CustomUserDetails userDetails, Long eventId){
    try{
      Member currentMember = securityUtil.getCurrentMember();

      CalendarEvent calendarEvent = findEventByIdAndValidate(eventId, currentMember);

      validateEventType(calendarEvent);

      MedicalResult medicalResult = medicalResultRepository.findByCalendarEvent(calendarEvent)
              .orElseThrow(() -> new BusinessException(MedicalResultErrorCode.MEDICAL_RESULT_NOT_FOUND));

      // 진료결과 삭제
      medicalResultRepository.delete(medicalResult);

      // 캘린더 이벤트도 삭제
      calendarRepository.delete(calendarEvent);

    } catch(BusinessException e){
      throw e;
    } catch(Exception e){
      throw new BusinessException(MedicalResultErrorCode.DATABASE_ERROR);
    }
  }

  /**
   * 가족 구성원의 공개된 진료결과 조회
   * @param userDetails 인증된 사용자의  정보
   * @return 가족 구성원의 공개된 진료 결과 목록
   */
  @Override
  @Transactional(readOnly = true)
  public List<FamilyMedicalResultResponse> getFamilyMedicalResult(CustomUserDetails userDetails) {
    try {
      Member currentMember = securityUtil.getCurrentMember();

      if (currentMember == null) {
        throw new BusinessException(MedicalResultErrorCode.ACCESS_DENIED);
      }

      // 가족 정보 확인
      if (currentMember.getFamily() == null) {
        throw new BusinessException(MedicalResultErrorCode.FAMILY_NOT_FOUND);
      }

      // 가족 구성원들의 공개된 진료 결과 조회 (본인 제외)
      List<MedicalResult> familyResults = medicalResultRepository
              .findByMemberFamilyIdAndIsPublicTrueAndMemberIdNot(
                      currentMember.getFamily().getId(),
                      currentMember.getId()
              );

      if (familyResults == null || familyResults.isEmpty()) {
        return new ArrayList<>();
      }

      return familyResults.stream()
              .filter(Objects::nonNull)  // null 체크
              .map(this::convertToFamilyDto)
              .filter(Objects::nonNull)  // 변환 결과도 null 체크
              .collect(Collectors.toList());

    } catch (BusinessException e) {
      throw e;
    } catch (Exception e) {
      throw new BusinessException(MedicalResultErrorCode.DATABASE_ERROR);
    }
  }

  /**
   * MedicalResult Entity FamilyMedicalResultResponse DTO로 변환
   * @param medicalResult 변환할 진료 결과 엔티티
   * @return 변환된 DTO
   */
  private FamilyMedicalResultResponse convertToFamilyDto(MedicalResult medicalResult) {
    if (medicalResult == null) {
      return null;
    }

    // 캘린더 이벤트 정보져오기
    CalendarEvent calendarEvent = medicalResult.getCalendarEvent();
    if (calendarEvent == null) {
      throw new BusinessException(MedicalResultErrorCode.CALENDAR_EVENT_NOT_FOUND);
    }

    // 회원 정보가져오기
    Member member = medicalResult.getMember();
    if (member == null) {
      throw new BusinessException(MedicalResultErrorCode.ACCESS_DENIED);
    }

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

    // 가족 공유 진료 결과 응답 DTO 생성 및 반환
    return FamilyMedicalResultResponse.builder()
            .resultId(medicalResult.getResultId())
            .eventId(calendarEvent.getEventId())
            .memberName(member.getUsername())
            .title(calendarEvent.getTitle())
            .memo(medicalResult.getMemo())
            .startDate(calendarEvent.getStartDate())
            .endDate(calendarEvent.getEndDate())
            .prescriptionImage(prescriptionImageDto)
            .medicineImage(medicineImageDto)
            .build();
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
