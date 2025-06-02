package emp.emp.medication.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicationDrugRequest {

  @NotBlank(message = "약물명은 필수 입력 사항입니다.")
  @Size(max = 50, message = "약물명은 최대 50자까지 입력해주세요.")
  private String drugName; // 약물명

  @NotBlank(message = "복용량은 필수 입력 사항입니다.")
  @Size(max = 100, message = "복용량은 최대 100자까지 입력해주세요.")
  private String dosage; // 복용량

}
