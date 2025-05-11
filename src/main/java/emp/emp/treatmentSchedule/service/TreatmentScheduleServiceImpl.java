package emp.emp.treatmentSchedule.service;

import emp.emp.auth.custom.CustomUserDetails;
import emp.emp.calendar.entity.CalendarEvent;
import emp.emp.calendar.repository.CalendarRepository;
import emp.emp.exception.BusinessException;
import emp.emp.member.entity.Member;
import emp.emp.treatmentSchedule.dto.request.TreatmentScheduleRequest;
import emp.emp.treatmentSchedule.dto.response.TreatmentScheduleResponse;
import emp.emp.treatmentSchedule.entity.TreatmentSchedule;
import emp.emp.treatmentSchedule.exception.TreatmentErrorCode;
import emp.emp.treatmentSchedule.repository.TreatmentScheduleRepository;
import emp.emp.util.security.SecurityUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class TreatmentScheduleServiceImpl implements TreatmentScheduleService {

  private final TreatmentScheduleRepository treatmentScheduleRepository;
  private final CalendarRepository calendarRepository;
  private final SecurityUtil securityUtil;

  @Override
  @Transactional
  public TreatmentScheduleResponse createTreatmentSchedule(CustomUserDetails userDetails, TreatmentScheduleRequest request) {
    try {
      // 유효성 검사
      validateRequest(request);

      // 현재 로그인한 사용자 정보 가져오기
      Member currentMember = securityUtil.getCurrentMember();

      // 연결할 캘린더 이벤트 찾기
      CalendarEvent calendarEvent = calendarRepository.findById(request.getEventId())
              .orElseThrow(() -> new BusinessException(TreatmentErrorCode.CALENDAR_EVENT_NOT_FOUND));

      // 캘린더이벤트 소유자 확인
      if (!calendarEvent.getMember().equals(currentMember)) {
        throw new BusinessException(TreatmentErrorCode.EVENT_ACCESS_DENIED);
      }

      // 진료일정 생성
      TreatmentSchedule treatmentSchedule = TreatmentSchedule.builder()
              .calendarEvent(calendarEvent)
              .member(currentMember)
              .isPublic(request.getIsPublic())
              .location(request.getLocation())
              .time(request.getTime())
              .memo(request.getMemo())
              .build();

      treatmentScheduleRepository.save(treatmentSchedule);

      return toResponse(treatmentSchedule);
    } catch (DataIntegrityViolationException e) {
      log.error("데이터 무결성 위반 오류 : {}", e.getMessage());
      throw new BusinessException(TreatmentErrorCode.DATABASE_ERROR);
    } catch (DataAccessException e) {
      log.error("데이터 접근 오류 : {}", e.getMessage());
      throw new BusinessException(TreatmentErrorCode.DATABASE_ERROR);
    } catch (BusinessException e) {
      throw e;
    } catch (Exception e) {
      log.error("진료일정 생성 중 예상치 못한 오류 발생!! : {}", e.getMessage(), e);
      throw new BusinessException(TreatmentErrorCode.INVALID_TREATMENT_DATA);
    }
  }

  @Override
  @Transactional
  public TreatmentScheduleResponse updateTreatmentSchedule(CustomUserDetails userDetails, Long treatmentId, TreatmentScheduleRequest request) {
    try {

      validateRequest(request);

      Member currentMember = securityUtil.getCurrentMember();

      // 진료일정 찾기 & 권한 확인
      TreatmentSchedule treatmentSchedule = findByIdAndValidate(treatmentId, currentMember);

      // 캘린더 이벤트가 변경된 경우 -> 새 이벤트 확인
      if (!treatmentSchedule.getCalendarEvent().getEventId().equals(request.getEventId())) {
        CalendarEvent newCalendarEvent = calendarRepository.findById(request.getEventId())
                .orElseThrow(() -> new BusinessException(TreatmentErrorCode.CALENDAR_EVENT_NOT_FOUND));

        // 새 이벤트의 소유자 확인
        if (!newCalendarEvent.getMember().equals(currentMember)) {
          throw new BusinessException(TreatmentErrorCode.EVENT_ACCESS_DENIED);
        }

        treatmentSchedule.setCalendarEvent(newCalendarEvent);
      }

      treatmentSchedule.update(request);

      return toResponse(treatmentSchedule);
    } catch (DataIntegrityViolationException e) {
      log.error("데이터 무결성 위반 오류 : {}", e.getMessage());
      throw new BusinessException(TreatmentErrorCode.DATABASE_ERROR);
    } catch (DataAccessException e) {
      log.error("데이터 접근 오류 : {}", e.getMessage());
      throw new BusinessException(TreatmentErrorCode.DATABASE_ERROR);
    } catch (BusinessException e) {
      throw e;
    } catch (Exception e) {
      log.error("진료일정 수정 중 예상치 못한 오류 발생!! : {}", e.getMessage(), e);
      throw new BusinessException(TreatmentErrorCode.INVALID_TREATMENT_DATA);
    }
  }

  @Override
  @Transactional
  public void deleteTreatmentSchedule(CustomUserDetails userDetails, Long treatmentId) {
    try {

      Member currentMember = securityUtil.getCurrentMember();

      TreatmentSchedule treatmentSchedule = findByIdAndValidate(treatmentId, currentMember);

      treatmentScheduleRepository.delete(treatmentSchedule);
    } catch (DataAccessException e) {
      log.error("데이터 접근 오류 : {}", e.getMessage());
      throw new BusinessException(TreatmentErrorCode.DATABASE_ERROR);
    } catch (BusinessException e) {
      throw e;
    } catch (Exception e) {
      log.error("진료일정 삭제 중 예상치 못한 오류 발생!! : {}", e.getMessage(), e);
      throw new BusinessException(TreatmentErrorCode.INVALID_TREATMENT_DATA);
    }
  }


  /**
   * 요청 데이터 유효성 검사
   * @param request 진료일정 요청 DTO
   * @throws BusinessException 유효성 검사 실패 시
   */
  private void validateRequest(TreatmentScheduleRequest request) {
    // eventId 검사
    if (request.getEventId() == null) {
      throw new BusinessException(TreatmentErrorCode.CALENDAR_EVENT_NOT_FOUND);
    }

    // 위치 검사
    if (request.getLocation() == null || request.getLocation().trim().isEmpty()) {
      throw new BusinessException(TreatmentErrorCode.INVALID_LOCATION);
    }

    // 시간 검사
    if (request.getTime() == null) {
      throw new BusinessException(TreatmentErrorCode.INVALID_TIME_FORMAT);
    }

    // 과거 시간 검사 (선택적)
    // if (request.getTime().isBefore(LocalDateTime.now())) {
    //     throw new BusinessException(TreatmentErrorCode.PAST_TIME_NOT_ALLOWED);
    // }

    // isPublic이 null인 경우 기본값 설정
    if (request.getIsPublic() == null) {
      request.setIsPublic(false);
    }
  }

  /**
   * 진료일정 ID로 조회 및 사용자 권한 검증
   * @param treatmentId 진료일정 ID
   * @param currentMember 현재 사용자
   * @return 진료일정 엔티티
   * @throws BusinessException 진료일정을 찾을 수 없거나 접근 권한이 없는 경우
   */
  private TreatmentSchedule findByIdAndValidate(Long treatmentId, Member currentMember) {
    // treatmentId 유효성 검사
    if (treatmentId == null) {
      throw new BusinessException(TreatmentErrorCode.TREATMENT_NOT_FOUND);
    }

    // 진료일정 찾기
    TreatmentSchedule treatmentSchedule = treatmentScheduleRepository.findById(treatmentId)
            .orElseThrow(() -> new BusinessException(TreatmentErrorCode.TREATMENT_NOT_FOUND));

    // 소유자 검증
    if (!treatmentSchedule.getMember().equals(currentMember)) {
      throw new BusinessException(TreatmentErrorCode.ACCESS_DENIED);
    }

    return treatmentSchedule;
  }

  /**
   * 진료일정 엔티티를 응답 DTO로 변환
   * @param treatmentSchedule 진료일정 엔티티
   * @return 진료일정 응답 DTO
   */
  private TreatmentScheduleResponse toResponse(TreatmentSchedule treatmentSchedule) {
    return TreatmentScheduleResponse.builder()
            .treatmentId(treatmentSchedule.getTreatmentId())
            .eventId(treatmentSchedule.getCalendarEvent().getEventId())
            .verifyId(treatmentSchedule.getMember().getVerifyId())
            .isPublic(treatmentSchedule.getIsPublic())
            .location(treatmentSchedule.getLocation())
            .time(treatmentSchedule.getTime())
            .memo(treatmentSchedule.getMemo())
            .eventTitle(treatmentSchedule.getCalendarEvent().getTitle())
            .build();
  }
}