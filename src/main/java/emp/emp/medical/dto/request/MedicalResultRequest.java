package emp.emp.medical.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicalResultRequest {

  private String memo;
  private Long prescriptionImageId;
  private Long  medicineImageId;
  private Boolean isPublic;

  public boolean isPublic() {
    return isPublic != null ? isPublic : false;
  }

}
