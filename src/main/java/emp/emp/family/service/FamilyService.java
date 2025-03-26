package emp.emp.family.service;

import emp.emp.family.dto.request.ChangeFamilyNameReq;
import emp.emp.family.dto.request.CreateFamilyReq;
import emp.emp.family.dto.request.JoinFamilyReq;
import emp.emp.family.dto.response.FamilyRes;

public interface FamilyService {

	/**
	 * 가족 생성 메서드
	 *
	 * @param request 가족 생성에 필요한 정보
	 */
	void createFamily(CreateFamilyReq request);

	/**
	 * 가족 가입 메서드
	 *
	 * @param request 가족 코드
	 */
	void joinFamily(JoinFamilyReq request);

	/**
	 * 가족 삭제 메서드 (head만 삭제 가능)
	 */
	void deleteFamily();

	/**
	 * 가족 이름 수정 메서드 (head만 수정 가능)
	 * @param request 바꿀 가족 이름
	 */
	void changeFamilyName(ChangeFamilyNameReq request);

	/**
	 * 가족 탈퇴 메서드 (head는 탈퇴 불가능)
	 */
	void exitFamily();

	/**
	 * 가족 정보 조회 메서드
	 * @return 가족 정보
	 */
	FamilyRes getFamilyInfo();
}
