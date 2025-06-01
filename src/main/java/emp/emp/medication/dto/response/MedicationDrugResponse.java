package emp.emp.medication.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MedicationDrugResponse {

  private Long drugId;
  private String drugName;
  private String dosage;

}
