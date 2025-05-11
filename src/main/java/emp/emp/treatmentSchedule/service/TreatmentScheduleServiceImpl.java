package emp.emp.treatmentSchedule.service;

import emp.emp.auth.custom.CustomUserDetails;
import emp.emp.calendar.entity.CalendarEvent;
import emp.emp.calendar.repository.CalendarRepository;
import emp.emp.member.entity.Member;
import emp.emp.treatmentSchedule.dto.request.TreatmentScheduleRequest;
import emp.emp.treatmentSchedule.dto.response.TreatmentScheduleResponse;
import emp.emp.treatmentSchedule.entity.TreatmentSchedule;
import emp.emp.treatmentSchedule.repository.TreatmentScheduleRepository;
import emp.emp.util.security.SecurityUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TreatmentScheduleServiceImpl implements TreatmentScheduleService {

  private final SecurityUtil securityUtil;
  private final CalendarRepository calendarRepository;
  private final TreatmentScheduleRepository treatmentScheduleRepository;

  @Override
  @Transactional
  public TreatmentScheduleResponse createTreatmentSchedule(CustomUserDetails userDetails, TreatmentScheduleRequest request){

    // 현재 로그인한 사용자의 정보 가져오기
    Member currentMember = securityUtil.getCurrentMember();

    // 연결한 캘린더이벤트 찾기
    CalendarEvent calendarEvent = calendarRepository.findById(request.getEventId())
            .orElseThrow(() -> new RuntimeException("캘린더이벤트를 찾을수 없슈"));

    // 캘린더이벤트 소유자 확인
    if(!calendarEvent.getMember().equals(currentMember)){
      throw new RuntimeException("접근권한 없슈");
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
  }




  /**
   * 진료일정 엔티티를 응답DTO로 변환
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
