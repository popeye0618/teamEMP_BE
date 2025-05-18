package emp.emp.medical.dto.response;

import emp.emp.common.dto.ImageDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 가족 공유 진료결과 조회 응답 DTO
 */
@Getter
@Builder
public class FamilyMedicalResultResponse {

  private Long resultId;
  private Long eventId;
  private String memberName; // 회원이름
  private String title; // 일정 제목
  private String memo;
  private LocalDateTime startDate;
  private LocalDateTime endDate;
  private ImageDto prescriptionImage; // 처방전이미지 정보
  private ImageDto medicalImage; // 약 이미지 정보

}
