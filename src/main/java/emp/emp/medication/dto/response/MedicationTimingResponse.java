package emp.emp.medication.dto.response;

import emp.emp.medication.enums.MedicationTimingType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MedicationTimingResponse {

  private Long timingId;
  private MedicationTimingType timingType;
  private String timingDescription; // 복약시기 설명
  private String precaution;

}
