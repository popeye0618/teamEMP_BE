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
import java.util.List;

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

  /**
   * 복약관리 조회
   * @param userDetails 인증된 사용자 정보
   * @param eventId 캘린더 이벤트 ID
   * @return 복약관리 정보
   */
  @GetMapping("/{eventId}")
  public ResponseEntity<Response<MedicationManagementResponse>> getMedication(
          @AuthenticationPrincipal CustomUserDetails userDetails,
          @PathVariable Long eventId
  ) {
    MedicationManagementResponse response = medicationService.getMedication(userDetails, eventId);

    return Response.ok(response).toResponseEntity();
  }

  /**
   * 복약관리 수정
   * @param userDetails
   * @param eventId
   * @param request
   * @return
   */
  @PutMapping("/{eventId}")
  public ResponseEntity<Response<MedicationManagementResponse>> updateMedication(
          @AuthenticationPrincipal CustomUserDetails userDetails,
          @PathVariable Long eventId,
          @RequestBody @Valid MedicationManagementRequest request
  ) {
    MedicationManagementResponse response = medicationService.updateMedication(userDetails, eventId, request);

    return Response.ok(response).toResponseEntity();
  }

  /**
   * 복약관리 삭제
   * @param userDetails 인증된 사용자의 정보
   * @param eventId 캘린더 이벤트 시퀀스ID
   * @return 삭제 완료 응답
   */
  @DeleteMapping("/{eventId}")
  public ResponseEntity<Response<Void>> deleteMedication(
          @AuthenticationPrincipal CustomUserDetails userDetails,
          @PathVariable Long eventId
  ) {
    medicationService.deleteMedication(userDetails, eventId);

    return Response.ok().toResponseEntity();
  }

  /**
   * 내 복약관리 목록 조회
   * @param userDetails 인증된 사용자의 정보
   * @return 내 복약관리 목록
   */
  @GetMapping
  public ResponseEntity<Response<List<MedicationManagementResponse>>> getMyMedications(
          @AuthenticationPrincipal CustomUserDetails userDetails
  ) {
    List<MedicationManagementResponse> response = medicationService.getMyMedications(userDetails);

    return Response.ok(response).toResponseEntity();
  }



}
