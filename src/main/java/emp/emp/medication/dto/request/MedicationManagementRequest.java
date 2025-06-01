package emp.emp.medication.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MedicationManagementRequest {

  @NotBlank(message = "병명은 필수 입력 사항입니다.")
  @Size(max = 10, message = "병명은 최대 10자까지만 입력가능합니다.")
  private String diseaseName; // 병명

  @NotNull(message = "복약 시작일은 필수 입력사항 입니다.")
  private LocalDate startDate; // 복약 시작일

  @NotNull(message = "복약 종료일은 필수 입력사항 입니다.")
  private LocalDate endDate;  // 복약 종료일




}
