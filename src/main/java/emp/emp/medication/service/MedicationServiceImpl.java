package emp.emp.medication.service;

import emp.emp.auth.custom.CustomUserDetails;
import emp.emp.calendar.entity.CalendarEvent;
import emp.emp.calendar.enums.CalendarEventType;
import emp.emp.calendar.repository.CalendarRepository;
import emp.emp.exception.BusinessException;
import emp.emp.medication.dto.request.MedicationManagementRequest;
import emp.emp.medication.dto.response.MedicationDrugResponse;
import emp.emp.medication.dto.response.MedicationManagementResponse;
import emp.emp.medication.dto.response.MedicationTimingResponse;
import emp.emp.medication.entity.MedicationManagement;
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

  @Override
  @Transactional
  public MedicationManagementResponse createMedication(CustomUserDetails userDetails, Long eventId, MedicationManagementRequest request){
    try{
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

}
