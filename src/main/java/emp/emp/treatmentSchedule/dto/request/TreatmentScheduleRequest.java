package emp.emp.treatmentSchedule.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 진료일정 등록 DTO
 */
@Getter
@Setter
public class TreatmentScheduleRequest {

  private Long eventId;
  private String location;
  private LocalDateTime time;
  private String memo;
  private Boolean isPublic;

}
