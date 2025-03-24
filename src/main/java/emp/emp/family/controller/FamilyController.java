package emp.emp.family.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import emp.emp.family.dto.request.ChangeFamilyNameReq;
import emp.emp.family.dto.request.CreateFamilyReq;
import emp.emp.family.dto.request.JoinFamilyReq;
import emp.emp.family.dto.response.FamilyRes;
import emp.emp.family.service.FamilyService;
import emp.emp.util.api_response.Response;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/user")
public class FamilyController {

	private final FamilyService familyService;

	/**
	 * 가족 생성 메서드
	 *
	 * @param request 가족 생성에 필요한 정보
	 * @return 200 ok
	 */
	@PostMapping("/family")
	public ResponseEntity<Response<Void>> createFamily(@RequestBody CreateFamilyReq request) {
		familyService.createFamily(request);

		return Response.ok().toResponseEntity();
	}

	/**
	 * 가족 가입 메서드 (가입 이후 가족 가입)
	 *
	 * @param request 가족 코드
	 * @return 200 ok
	 */
	@PostMapping("/family/join")
	public ResponseEntity<Response<Void>> joinFamily(@RequestBody JoinFamilyReq request) {
		familyService.joinFamily(request);

		return Response.ok().toResponseEntity();
	}

	/**
	 * 가족 삭제 메서드 (head만 삭제 가능)
	 */
	@DeleteMapping("/family")
	public ResponseEntity<Response<Void>> deleteFamily() {
		familyService.deleteFamily();

		return Response.ok().toResponseEntity();
	}

	/**
	 * 가족 이름 수정 메서드 (head만 수정 가능)
	 * @param request 바꿀 가족 이름
	 * @return 200 ok
	 */
	@PatchMapping("/family/name")
	public ResponseEntity<Response<Void>> changeFamilyName(@RequestBody ChangeFamilyNameReq request) {
		familyService.changeFamilyName(request);

		return Response.ok().toResponseEntity();
	}

	/**
	 * 가족 탈퇴 메서드 (head는 탈퇴 불가능)
	 * @return 200 ok
	 */
	@DeleteMapping("/family/exit")
	public ResponseEntity<Response<Void>> changeFamilyName() {
		familyService.exitFamily();

		return Response.ok().toResponseEntity();
	}


	@GetMapping("/family")
	public ResponseEntity<Response<FamilyRes>> getFamilyInfo() {
		FamilyRes familyInfo = familyService.getFamilyInfo();

		return Response.ok(familyInfo).toResponseEntity();
	}

}
