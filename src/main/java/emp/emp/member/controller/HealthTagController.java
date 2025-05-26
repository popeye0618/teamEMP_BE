package emp.emp.member.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import emp.emp.member.dto.request.HealthTagReq;
import emp.emp.member.dto.response.HealthTagRes;
import emp.emp.member.service.HealthTagService;
import emp.emp.util.api_response.Response;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/user")
public class HealthTagController {

	private final HealthTagService healthTagService;

	@PostMapping("/health-tag")
	public ResponseEntity<Response<List<HealthTagRes>>> createHealthTag(@RequestBody List<HealthTagReq> healthTagReqList) {

		List<HealthTagRes> healthTagResList = healthTagService.createHealthTag(healthTagReqList);

		return Response.ok(healthTagResList).toResponseEntity();
	}

	@DeleteMapping("/health-tag/{tagId}")
	public ResponseEntity<Response<Void>> deleteHealthTag(@PathVariable Long tagId) {
		healthTagService.deleteHealthTag(tagId);

		return Response.ok().toResponseEntity();
	}

	@GetMapping("/health-tag")
	public ResponseEntity<Response<List<HealthTagRes>>> getMyHealthTags() {
		List<HealthTagRes> healthTags = healthTagService.getMyHealthTags();

		return Response.ok(healthTags).toResponseEntity();
	}

}
