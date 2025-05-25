package emp.emp.medical.service;

import emp.emp.auth.custom.CustomUserDetails;
import emp.emp.medical.dto.request.MedicalResultRequest;
import emp.emp.medical.dto.response.MedicalResultResponse;

public interface MedicalResultService {

  /**
   * 진료 결과 등록
   * @param userDetails 인증된 사용자의 정보
   * @param eventId 캘린더 이벤트 시퀀스 Id
   * @param request 진료결과 등록 요청 데이터
   * @return 등록된 진료 결과 정보
   */
  MedicalResultResponse createMedicalResult(CustomUserDetails userDetails, Long eventId, MedicalResultRequest request);

  /**
   * 진료 결과( medical_result) 조회
   * @param userDetails 인증된 사용자의 정보
   * @param eventId  캘린더 이벤트 시퀀스 Id
   * @return 진료 결과 정보
   */
  MedicalResultResponse getMedicalResult(CustomUserDetails userDetails, Long eventId);

  /**
   * 진료 결과 수정
   * @param userDetails 인증된 사용자의 정보
   * @param eventId 캘린더 이벤트 시퀀스 Id
   * @param request 진료 결과 수정 요청 데이터
   * @return 수정된 진료 결과 정보
   */
  MedicalResultResponse updateMedicalResult(CustomUserDetails userDetails, Long eventId, MedicalResultRequest request);

  /**
   * 진료 결과 삭제
   * @param userDetails 인증된 사용자의 정보
   * @param eventId 캘린터 이벤트 시퀀스 ID
   */
  void deleteMedicalResult(CustomUserDetails userDetails, Long eventId);

}
