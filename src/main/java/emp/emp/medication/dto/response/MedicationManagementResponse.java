package emp.emp.medication.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class MedicationManagementResponse {

  private Long mdeicationId;
  private Long eventId;
  private String verifyId;
  private String diseaseName;
  private LocalDate startDate;
  private LocalDate endDate;
  private Boolean isPublic;
  private String title; // 캘린더 이벤트의 일정 제목
  private LocalDateTime calendarStartDate; // 캘린더 시작일시
  private LocalDateTime calendarEndDate; // 캘린더 종료 일시

}
