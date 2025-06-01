package emp.emp.medication.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class FamilyMedicationResponse {

  private Long medicationId;
  private Long eventId;
  private String memberName;
  private String diseaseName;
  private LocalDate startDate;
  private LocalDate endDate;
  private String title; // 캘린더 일정의 제목
  private LocalDateTime calendarStartDate; // 캘린더이벤트 시작일시
  private LocalDateTime calendarEndDate; // 캘린더이벤트 종료일시

  private List<MedicationDrugResponse> drugs; // 약물 정보 목록
  private List<MedicationTimingResponse> timings; // 복약시기 목록


}
