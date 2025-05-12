package emp.emp.treatmentSchedule.controller;

import emp.emp.auth.custom.CustomUserDetails;
import emp.emp.exception.BusinessException;
import emp.emp.treatmentSchedule.dto.request.TreatmentScheduleRequest;
import emp.emp.treatmentSchedule.dto.response.TreatmentScheduleResponse;
import emp.emp.treatmentSchedule.exception.TreatmentErrorCode;
import emp.emp.treatmentSchedule.service.TreatmentScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/api/auth/user/treatment")
@RequiredArgsConstructor
public class TreatmentScheduleController {

  private final TreatmentScheduleService treatmentScheduleService;

  /**
   * 진료일정 등록
   * @param userDetails 사용자 정보
   * @param request
   * @return
   */
  @PostMapping
  public ResponseEntity<TreatmentScheduleResponse> createTreatmentSchedule(
          @AuthenticationPrincipal CustomUserDetails userDetails,
          @RequestBody TreatmentScheduleRequest request
  ) {
    log.info("진료일정 등록 요청: {}", request);

    if (request.getEventId() == null) {
      throw new BusinessException(TreatmentErrorCode.CALENDAR_EVENT_NOT_FOUND);
    }

    if (request.getLocation() == null || request.getLocation().trim().isEmpty()) {
      throw new BusinessException(TreatmentErrorCode.INVALID_LOCATION);
    }

    if (request.getTime() == null) {
      throw new BusinessException(TreatmentErrorCode.INVALID_TIME_FORMAT);
    }

    TreatmentScheduleResponse response = treatmentScheduleService.createTreatmentSchedule(userDetails, request);
    return ResponseEntity.ok(response);
  }

  /**
   * 진료일정 수정
   * @param userDetails 인증된 사용자 정보
   * @param treatmentId 진료일정 ID
   * @param request 진료일정 수정 요청 DTO
   * @return 수정된 진료일정 응답 DTO
   */
  @PutMapping("/{treatmentId}")
  public ResponseEntity<TreatmentScheduleResponse> updateTreatmentSchedule(
          @AuthenticationPrincipal CustomUserDetails userDetails,
          @PathVariable Long treatmentId,
          @RequestBody TreatmentScheduleRequest request
  ) {
    log.info("진료일정 수정 요청 - ID: {}, 요청: {}", treatmentId, request);

    if (treatmentId == null) {
      throw new BusinessException(TreatmentErrorCode.TREATMENT_NOT_FOUND);
    }

    TreatmentScheduleResponse response = treatmentScheduleService.updateTreatmentSchedule(userDetails, treatmentId, request);
    return ResponseEntity.ok(response);
  }

  /**
   * 진료일정 삭제
   * @param userDetails 인증된 사용자 정보
   * @param treatmentId 진료일정 ID
   * @return 성공 응답
   */
  @DeleteMapping("/{treatmentId}")
  public ResponseEntity<Void> deleteTreatmentSchedule(
          @AuthenticationPrincipal CustomUserDetails userDetails,
          @PathVariable Long treatmentId
  ) {
    log.info("진료일정 삭제 요청 - ID: {}", treatmentId);

    if (treatmentId == null) {
      throw new BusinessException(TreatmentErrorCode.TREATMENT_NOT_FOUND);
    }

    treatmentScheduleService.deleteTreatmentSchedule(userDetails, treatmentId);
    return ResponseEntity.ok().build();
  }

}