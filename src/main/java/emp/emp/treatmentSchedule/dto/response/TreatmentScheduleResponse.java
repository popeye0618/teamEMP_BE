package emp.emp.treatmentSchedule.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 진료일정 조회용DTO
 */
@Getter
@Builder
public class TreatmentScheduleResponse {

  private Long treatmentId;
  private Long eventId;
  private String verifyId; // 회원식별자
  private String location;
  private LocalDateTime time;
  private String memo;
  private Boolean isPublic;
  private String eventTitle; // 연결된 캘린더이벤트의 제목

}
