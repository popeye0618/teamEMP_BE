package emp.emp.treatmentSchedule.service;

import emp.emp.auth.custom.CustomUserDetails;
import emp.emp.treatmentSchedule.dto.request.TreatmentScheduleRequest;
import emp.emp.treatmentSchedule.dto.response.TreatmentScheduleResponse;

public interface TreatmentScheduleService {

  /**
   * 진료일정 등록
   * @param userDetails 사용자의 정보
   * @param request 등록요청DTO
   * @return 응답DTO
   */
  TreatmentScheduleResponse createTreatmentSchedule(CustomUserDetails userDetails, TreatmentScheduleRequest request);

  /**
   * 진료일정 수정
   * @param userDetails
   * @param treatmentId
   * @param request
   * @return
   */
  TreatmentScheduleResponse updateTreatmentSchedule(CustomUserDetails userDetails, Long treatmentId, TreatmentScheduleRequest request);

  /**
   * 진료일정 삭제
   * @param userDetails
   * @param treatmentId
   */
  void deleteTreatmentSchedule(CustomUserDetails userDetails, Long treatmentId);

}