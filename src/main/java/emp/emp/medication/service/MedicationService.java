package emp.emp.medication.service;

import emp.emp.auth.custom.CustomUserDetails;
import emp.emp.medication.dto.request.MedicationManagementRequest;
import emp.emp.medication.dto.response.MedicationManagementResponse;
import java.util.List;

public interface MedicationService {

  /**
   * 복약관리 등록
   * @param userDetails 인증된 사용자의 정보
   * @param eventId 캘린더 이벤트의 시퀀스 ID
   * @param request 복약관리 등록 요청하는 데이터들
   * @return 등록된 복약관리의 정보
   */
  MedicationManagementResponse createMedication(CustomUserDetails userDetails, Long eventId, MedicationManagementRequest request);

  /**
   * 복약관리 조회
   * @param userDetails 인증된 사용자의 정보
   * @param eventId 캘린더 이벤트 시퀀스 ID
   * @return 복약관리 정보
   */
  MedicationManagementResponse getMedication(CustomUserDetails userDetails, Long eventId);

  /**
   * 복약관리 수정
   * @param userDetails 인증된 사용자의 정보
   * @param eventId 캘린더 이벤트 시퀀스 ID
   * @param request 복약관리 수정요청 데이터
   * @return 수정된 복약관리 정보
   */
  MedicationManagementResponse updateMedication(CustomUserDetails userDetails, Long eventId, MedicationManagementRequest request);

  /**
   * 복약관리 삭제
   * @param userDetails 인증된 사용자의 정보
   * @param eventId 캘린더 이벤트 시퀀스 ID
   */
  void deleteMedication(CustomUserDetails userDetails, Long eventId);

  /**
   * 내 복약관리 목록 조회
   * @param userDetails 인증된 사용자의 정보
   * @return 복약관리 목록
   */
  List<MedicationManagementResponse> getMyMedications(CustomUserDetails userDetails);

}
