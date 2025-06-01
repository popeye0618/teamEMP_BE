package emp.emp.medication.controller;

import emp.emp.auth.custom.CustomUserDetails;
import emp.emp.medication.dto.request.MedicationManagementRequest;
import emp.emp.medication.dto.response.MedicationManagementResponse;
import emp.emp.medication.service.MedicationService;
import emp.emp.util.api_response.Response;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/user/medications")
@RequiredArgsConstructor
public class MedicationController {

  private final MedicationService medicationService; // 복약관리 서비스

  /**
   * 복약관리 등록
   * @param userDetails 인증된 사용자 정보
   * @param eventId 캘린더 이벤트 ID
   * @param request 복약관리 등록 요청 데이터
   * @return 등록된 복약관리 정보
   */
  @PostMapping("/{eventId}")
  public ResponseEntity<Response<MedicationManagementResponse>> createMedication(
          @AuthenticationPrincipal CustomUserDetails userDetails,
          @PathVariable Long eventId,
          @RequestBody
          @Valid MedicationManagementRequest request
  ) {
    MedicationManagementResponse response = medicationService.createMedication(userDetails, eventId, request);

    return Response.ok(response).toResponseEntity();
  }

}
