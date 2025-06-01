package emp.emp.medication.dto.request;

import emp.emp.medication.enums.MedicationTimingType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicationTimingRequest {

  @NotNull(message = "복약시기의 타입은 필수 선택 사항입니다.")
  private MedicationTimingType medicationTimingType; // 복약시기 타입(MORNING, LUNCH, EVENING)

  @Size(max = 500, message = "주의사항은 최대 500자까지 입력해주세요.")
  private String precaution; // 주의사항(선택임)

}
