package emp.emp.medication.service;

import emp.emp.auth.custom.CustomUserDetails;
import emp.emp.medication.dto.request.MedicationManagementRequest;
import emp.emp.medication.dto.response.MedicationManagementResponse;

public interface MedicationService {

  /**
   * 복약관리 등록
   * @param userDetails 인증된 사용자의 정보
   * @param eventId 캘린더 이벤트의 시퀀스 ID
   * @param request 복약관리 등록 요청하는 데이터들
   * @return 등록된 복약관리의 정보
   */
  MedicationManagementResponse createMedication(CustomUserDetails userDetails, Long eventId, MedicationManagementRequest request);

}
