package emp.emp.health.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import emp.emp.health.dto.request.HealthRecordReq;
import emp.emp.health.dto.response.HealthRecordRes;
import emp.emp.health.service.health.HealthService;
import emp.emp.util.api_response.Response;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/user")
public class HealthController {

	private final HealthService healthService;

	/**
	 * 건강 정보 등록
	 * @param request
	 * @return
	 */
	@PostMapping("/health")
	public ResponseEntity<Response<Void>> record(@RequestBody HealthRecordReq request) {
		healthService.recordHealth(request);

		return Response.ok().toResponseEntity();
	}

	/**
	 * 주간 건강 정보 조회
	 * @param verifyId
	 * @param year
	 * @param month
	 * @param week
	 * @param type
	 * @return
	 */
	@GetMapping("/health/weekly/{verifyId}/{year}/{month}/{week}/{type}")
	public HealthRecordRes getWeeklyRecords(
		@PathVariable String verifyId,
		@PathVariable int year,
		@PathVariable int month,
		@PathVariable int week,
		@PathVariable String type) {

		return healthService.getWeeklyHealthRecords(verifyId, year, month, week, type);
	}

	/**
	 * 월간 건강 정보 조회
	 * @param verifyId
	 * @param year
	 * @param month
	 * @param type
	 * @return
	 */
	@GetMapping("/health/weekly/{verifyId}/{year}/{month}/{type}")
	public HealthRecordRes getMonthlyRecords(
		@PathVariable String verifyId,
		@PathVariable int year,
		@PathVariable int month,
		@PathVariable String type) {

		return healthService.getMonthlyHealthRecords(verifyId, year, month, type);
	}
}
