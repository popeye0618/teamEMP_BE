package emp.emp.medication.service;

import emp.emp.auth.custom.CustomUserDetails;
import emp.emp.calendar.entity.CalendarEvent;
import emp.emp.calendar.enums.CalendarEventType;
import emp.emp.calendar.repository.CalendarRepository;
import emp.emp.exception.BusinessException;
import emp.emp.medication.dto.request.MedicationDrugRequest;
import emp.emp.medication.dto.request.MedicationManagementRequest;
import emp.emp.medication.dto.request.MedicationTimingRequest;
import emp.emp.medication.dto.response.MedicationDrugResponse;
import emp.emp.medication.dto.response.MedicationManagementResponse;
import emp.emp.medication.dto.response.MedicationTimingResponse;
import emp.emp.medication.entity.MedicationDrug;
import emp.emp.medication.entity.MedicationManagement;
import emp.emp.medication.entity.MedicationTiming;
import emp.emp.medication.exception.MedicationErrorCode;
import emp.emp.medication.repository.MedicationManagementRepository;
import emp.emp.member.entity.Member;
import emp.emp.util.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MedicationServiceImpl implements MedicationService{

  private final SecurityUtil securityUtil;
  private final CalendarRepository calendarRepository;
  private final MedicationManagementRepository medicationManagementRepository;

  /**
   *
   * @param userDetails 인증된 사용자의 정보
   * @param eventId 캘린더 이벤트의 시퀀스 ID
   * @param request 복약관리 등록 요청하는 데이터들
   * @return
   */
  @Override
  @Transactional
  public MedicationManagementResponse createMedication(CustomUserDetails userDetails, Long eventId, MedicationManagementRequest request) {
    try {
      // 현재 로그인한 회원 정보 가져오기
      Member currentMember = securityUtil.getCurrentMember();

      // 캘린더 이벤트 조회 & 소유권 검증
      CalendarEvent calendarEvent = findEventByIdAndValidate(eventId, currentMember);

      // 이벤트 타입 확인
      validateEventType(calendarEvent);

      // 이미 복약관리가 등록되어 있는지 확인
      medicationManagementRepository.findByCalendarEvent(calendarEvent)
              .ifPresent(medication -> {
                throw new BusinessException(MedicationErrorCode.MEDICATION_ALREADY_EXISTS);
              });

      // 요청 데이터의 유효성 검증
      validateRequest(request);

      // 복약관리Entity 생성
      MedicationManagement medicationManagement = MedicationManagement.builder()
              .calendarEvent(calendarEvent)
              .member(currentMember)
              .diseaseName(request.getDiseaseName())
              .startDate(request.getStartDate())
              .endDate(request.getEndDate())
              .isPublic(request.getIsPublic())
              .build();

      // 약물 정보들 추가
      for (MedicationDrugRequest drugRequest : request.getDrugs()) {
        MedicationDrug drug = MedicationDrug.builder()
                .drugName(drugRequest.getDrugName())
                .dosage(drugRequest.getDosage())
                .build();
        medicationManagement.addDrug(drug); // 양방향 관계 설정과 함께 추가
      }

      // 복약 시기들 추가
      for (MedicationTimingRequest timingRequest : request.getTimings()) {
        MedicationTiming timing = MedicationTiming.builder()
                .timingType(timingRequest.getMedicationTimingType())
                .precaution(timingRequest.getPrecaution())
                .build();
        medicationManagement.addTiming(timing);
      }

      // 복약관리 저장
      medicationManagementRepository.save(medicationManagement);

      // 응답 DTO로
      return convertToResponse(medicationManagement);

    } catch (BusinessException e) {
      throw e;
    } catch (Exception e) {
      log.error("복약관리 등록하는데 오류 발생", e);
      throw new BusinessException(MedicationErrorCode.DATABASE_ERROR);
    }
  }

  /**
   * 복약관리 조회
   * @param userDetails 인증된 사용자 정보
   * @param eventId 캘린더 이벤트 ID
   * @return 복약관리 정보
   */
  @Override
  @Transactional(readOnly = true)
  public MedicationManagementResponse getMedication(CustomUserDetails userDetails, Long eventId) {
    try {
      // 현재 로그인한 회원정보 가져오기
      Member currentMember = securityUtil.getCurrentMember();

      // 캘린더 이벤트 조회 & 소유권 검증
      CalendarEvent calendarEvent = findEventByIdAndValidate(eventId, currentMember);

      // 이벤트 타입 확인
      validateEventType(calendarEvent);

      // 복약관리 조회
      MedicationManagement medicationManagement = medicationManagementRepository.findByCalendarEvent(calendarEvent)
              .orElseThrow(() -> new BusinessException(MedicationErrorCode.MEDICATION_NOT_FOUND));

      // 응답 DTO로
      return convertToResponse(medicationManagement);

    } catch (BusinessException e) {
      throw e;
    } catch (Exception e) {
      log.error("복약관리 조회하는데 오류 발생", e);
      throw new BusinessException(MedicationErrorCode.DATABASE_ERROR);
    }
  }

  /**
   * 복약관리 수정
   * @param userDetails 인증된 사용자의 정보
   * @param eventId 캘린더 이벤트 시퀀스 ID
   * @param request 복약관리 수정요청 데이터
   * @return
   */
  @Override
  @Transactional
  public MedicationManagementResponse updateMedication(CustomUserDetails userDetails, Long eventId, MedicationManagementRequest request){

    try{
      Member currentMember = securityUtil.getCurrentMember();

      CalendarEvent calendarEvent = findEventByIdAndValidate(eventId, currentMember);

      validateEventType(calendarEvent);

      MedicationManagement medicationManagement = medicationManagementRepository.findByCalendarEvent(calendarEvent)
              .orElseThrow(() -> new BusinessException(MedicationErrorCode.MEDICATION_NOT_FOUND));

      validateRequest(request);

      // 기본 정보 수정하기
      medicationManagement.setDiseaseName(request.getDiseaseName());
      medicationManagement.setStartDate(request.getStartDate());
      medicationManagement.setEndDate(request.getEndDate());
      medicationManagement.setIsPublic(request.getIsPublic());

      // 기존의 약물 정보와 복약 시기 모두 삭제
      medicationManagement.clearDetails();

      // 새로운 약물 정보들 추가
      for (MedicationDrugRequest drugRequest : request.getDrugs()) {
        MedicationDrug drug = MedicationDrug.builder()
                .drugName(drugRequest.getDrugName())
                .dosage(drugRequest.getDosage())
                .build();
        medicationManagement.addDrug(drug); // 양방향 관계 설정 및 함께 추가
      }

      // 새로운 복약 시기들 추가
      for (MedicationTimingRequest timingRequest : request.getTimings()) {
        MedicationTiming timing = MedicationTiming.builder()
                .timingType(timingRequest.getMedicationTimingType())
                .precaution(timingRequest.getPrecaution())
                .build();
        medicationManagement.addTiming(timing);
      }
      return convertToResponse(medicationManagement);
    }catch(BusinessException e){
      throw e;
    } catch(Exception e){
      log.error("복약관리 수정하는데 오류 발생", e);
      throw new BusinessException(MedicationErrorCode.DATABASE_ERROR);
    }

  }




  // ======================================================

  /**
   * 캘린더 이벤트 조회 & 소유권 검증
   * @param eventId
   * @param currentMember
   * @return
   */
  private CalendarEvent findEventByIdAndValidate(Long eventId,Member currentMember) {
    // 캘린더 이벤트 조회
    CalendarEvent calendarEvent = calendarRepository.findById(eventId)
            .orElseThrow(() -> new BusinessException(MedicationErrorCode.CALENDAR_EVENT_NOT_FOUND));

    // 로그인한 사용자의 일정인지 확인
    if(!calendarEvent.getMember().equals(currentMember)){
      throw new BusinessException(MedicationErrorCode.ACCESS_DENIED);
    }
    return calendarEvent;
  }

  /**
   * 이벤트 타입이 MEDICATION(복약관리)인지 확인
   * @param calendarEvent
   */
  private void validateEventType(CalendarEvent calendarEvent){
    if(calendarEvent.getEventType() != CalendarEventType.MEDICATION){
      throw new BusinessException(MedicationErrorCode.EVENT_TYPE_INVALID);
    }
  }

  /**
   * MedicationManagement 엔티티를 MedicationManagementResponse DTO로 변환
   * @param medicationManagement 변환할 복약관리 Entity
   * @return 변환된 응답 DTO
   */
  private MedicationManagementResponse convertToResponse(MedicationManagement medicationManagement) {
    // 캘린더 이벤트 정보 가져오기
    CalendarEvent calendarEvent = medicationManagement.getCalendarEvent();

    // 약물 정보 DTO 리스트로 변환
    List<MedicationDrugResponse> drugResponses = medicationManagement.getDrugs().stream()
            .map(drug -> MedicationDrugResponse.builder()
                    .drugId(drug.getDrugId())
                    .drugName(drug.getDrugName())
                    .dosage(drug.getDosage())
                    .build())
            .collect(Collectors.toList());

    // 복약 시기 DTO 리스트로 변환
    List<MedicationTimingResponse> timingResponses = medicationManagement.getTimings().stream()
            .map(timing -> MedicationTimingResponse.builder()
                    .timingId(timing.getTimingId())
                    .timingType(timing.getTimingType())
                    .timingDescription(timing.getTimingType().getDescription())
                    .precaution(timing.getPrecaution())
                    .build())
            .collect(Collectors.toList());

    // 복약관리 응답 DTO 생성 & 반환
    return MedicationManagementResponse.builder()
            .mdeicationId(medicationManagement.getMedicationId())
            .eventId(calendarEvent.getEventId())
            .verifyId(calendarEvent.getMember().getVerifyId())
            .diseaseName(medicationManagement.getDiseaseName())
            .startDate(medicationManagement.getStartDate())
            .endDate(medicationManagement.getEndDate())
            .isPublic(medicationManagement.getIsPublic())
            .title(calendarEvent.getTitle())
            .calendarStartDate(calendarEvent.getStartDate())
            .calendarEndDate(calendarEvent.getEndDate())
            .drugs(drugResponses)
            .timings(timingResponses)
            .build();
  }

  /**
   * 복약관리 요청 데이터 유효성 검증
   * @param request 검증할 요청 데이터
   */
  private void validateRequest(MedicationManagementRequest request) {
    // 날짜 범위 유효성 검증
    if (!request.isValidDateRange()) {
      throw new BusinessException(MedicationErrorCode.INVALID_DATE_RANGE);
    }

    // 약물 목록이 비어있는지 확인
    if (request.getDrugs() == null || request.getDrugs().isEmpty()) {
      throw new BusinessException(MedicationErrorCode.DRUG_LIST_EMPTY);
    }

    // 복약 시기 목록이 비어있는지 확인
    if (request.getTimings() == null || request.getTimings().isEmpty()) {
      throw new BusinessException(MedicationErrorCode.TIMING_LIST_EMPTY);
    }
  }

}
