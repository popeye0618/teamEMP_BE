package emp.emp.medical.dto.response;

import emp.emp.common.dto.ImageDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class MedicalResultResponse {

  private Long resultId;
  private Long eventId;
  private String verifyId; // 회원식별
  private String title; // 일정 제목
  private String memo;
  private LocalDateTime startDate;
  private LocalDateTime endDate;
  private ImageDto prescriptionImage; // 처방전이미지 정보
  private ImageDto medicalImage; // 약 이미지 정보
  private boolean isPublic;

}
