package emp.emp.member.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import emp.emp.auth.custom.CustomUserDetails;
import emp.emp.member.entity.Member;
import emp.emp.util.api_response.Response;
import emp.emp.util.security.SecurityUtil;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MemberController {

	private final SecurityUtil securityUtil;

	@GetMapping("/example")
	public ResponseEntity<Response<Map<String, Object>>> example(
		@AuthenticationPrincipal CustomUserDetails userDetails) {
		Map<String, Object> profile = new HashMap<>();
		profile.put("verifyId", userDetails.getName());
		profile.put("email", userDetails.getEmail());
		profile.put("role", userDetails.getAuthorities().iterator().next().getAuthority());

		// 이외의 정보가 필요해서 Member 객체가 필요한 경우
		Member member = securityUtil.getCurrentMember();

		return Response.ok(profile).toResponseEntity();
	}
}
