package emp.emp.medical.controller;

import emp.emp.auth.custom.CustomUserDetails;
import emp.emp.medical.dto.request.MedicalResultRequest;
import emp.emp.medical.dto.response.MedicalResultResponse;
import emp.emp.medical.service.MedicalResultService;
import emp.emp.util.api_response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/user/medical-results")
@RequiredArgsConstructor
public class MedicalResultController {

  private final MedicalResultService medicalResultService;

  /**
   * 진료 결과 등록
   * @param userDetails 인증된 사용자 정보
   * @param eventId 캘린더 이벤트 ID
   * @param request 진료 결과 등록 요청 데이터
   * @return 등록된 진료 결과 정보
   */
  @PostMapping("/{eventId}")
  public ResponseEntity<Response<MedicalResultResponse>> createMedicalResult(
          @AuthenticationPrincipal CustomUserDetails userDetails,
          @PathVariable Long eventId,
          @RequestBody MedicalResultRequest request
  ) {
    MedicalResultResponse response = medicalResultService.createMedicalResult(userDetails, eventId, request);
    return Response.ok(response).toResponseEntity();
  }

}
