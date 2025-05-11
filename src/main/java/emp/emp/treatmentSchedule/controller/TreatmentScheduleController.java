package emp.emp.treatmentSchedule.controller;

import emp.emp.auth.custom.CustomUserDetails;
import emp.emp.treatmentSchedule.dto.request.TreatmentScheduleRequest;
import emp.emp.treatmentSchedule.dto.response.TreatmentScheduleResponse;
import emp.emp.treatmentSchedule.service.TreatmentScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
  public TreatmentScheduleResponse createTreatmentSchedule(
          @AuthenticationPrincipal CustomUserDetails userDetails,
          @RequestBody TreatmentScheduleRequest request
  ) {
    return treatmentScheduleService.createTreatmentSchedule(userDetails, request);
  }

}
