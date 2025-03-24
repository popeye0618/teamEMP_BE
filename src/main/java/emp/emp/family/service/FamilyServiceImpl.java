package emp.emp.family.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import emp.emp.exception.BusinessException;
import emp.emp.family.dto.request.ChangeFamilyNameReq;
import emp.emp.family.dto.request.CreateFamilyReq;
import emp.emp.family.dto.request.JoinFamilyReq;
import emp.emp.family.dto.response.FamilyMemberRes;
import emp.emp.family.dto.response.FamilyRes;
import emp.emp.family.entity.Family;
import emp.emp.family.exception.FamilyErrorCode;
import emp.emp.family.repository.FamilyRepository;
import emp.emp.member.entity.Member;
import emp.emp.util.security.SecurityUtil;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FamilyServiceImpl implements FamilyService {

	private final FamilyRepository familyRepository;
	private final SecurityUtil securityUtil;

	/**
	 * 가족 생성 메서드
	 *
	 * @param request 가족 생성에 필요한 정보
	 */
	@Override
	@Transactional
	public void createFamily(CreateFamilyReq request) {

		Member currentMember = securityUtil.getCurrentMember();

		validateFamilyIsNull(currentMember);

		Family family = Family.builder()
			.name(request.getName())
			.head(currentMember)
			.code(createValidCode())
			.build();

		currentMember.setFamily(family);

		familyRepository.save(family);
	}

	/**
	 * 가족 가입 메서드
	 *
	 * @param request 가족 코드
	 */
	@Override
	@Transactional
	public void joinFamily(JoinFamilyReq request) {

		Member currentMember = securityUtil.getCurrentMember();

		validateFamilyIsNull(currentMember);

		Family family = familyRepository.findByCode(request.getCode())
			.orElseThrow(() -> new BusinessException(FamilyErrorCode.INVALID_FAMILY_CODE));

		family.addMember(currentMember);
	}

	/**
	 * 가족 삭제 메서드 (head만 삭제 가능)
	 */
	@Override
	@Transactional
	public void deleteFamily() {

		Member currentMember = securityUtil.getCurrentMember();

		Family family = validateFamilyHead(currentMember);

		currentMember.setFamily(null);

		for (Member member : family.getMembers()) {
			member.setFamily(null);
		}

		familyRepository.delete(family);
	}

	/**
	 * 가족 이름 수정 메서드 (head만 수정 가능)
	 *
	 * @param request 바꿀 가족 이름
	 */
	@Override
	@Transactional
	public void changeFamilyName(ChangeFamilyNameReq request) {

		Member currentMember = securityUtil.getCurrentMember();

		Family family = validateFamilyHead(currentMember);

		family.changeName(request.getName());

	}

	/**
	 * 가족 탈퇴 메서드 (head는 탈퇴 불가능)
	 */
	@Override
	@Transactional
	public void exitFamily() {

		Member currentMember = securityUtil.getCurrentMember();

		validateFamilyIsNotNull(currentMember);

		Family family = currentMember.getFamily();

		if (family.getHead().equals(currentMember)) {
			throw new BusinessException(FamilyErrorCode.CANT_EXIT_FAMILY);
		}

		currentMember.setFamily(null);
	}

	/**
	 * 가족 정보 조회 메서드
	 * todo: 건강 관리 개발 후 건강 상태 설정하기 (현재는 null로 설정)
	 *
	 * @return 가족 정보
	 */
	@Override
	public FamilyRes getFamilyInfo() {

		Member currentMember = securityUtil.getCurrentMember();

		validateFamilyIsNotNull(currentMember);

		Family family = currentMember.getFamily();

		return buildFamilyRes(family);
	}

	/**
	 * 유효한 가족 코드 생성 메서드
	 * @return 유효한 가족 코드
	 */
	private String createValidCode() {
		while (true) {
			String code = UUID.randomUUID().toString().substring(0, 6).toUpperCase();

			if (!familyRepository.existsByCode(code)) {
				return code;
			}
		}

	}

	/**
	 * 가족 head 여부 검사
	 *
	 * @param currentMember 현재 유저
	 * @return 현재 유저가 속한 가족
	 */
	private Family validateFamilyHead(Member currentMember) {
		validateFamilyIsNotNull(currentMember);

		Family family = currentMember.getFamily();

		if (!family.getHead().equals(currentMember)) {
			throw new BusinessException(FamilyErrorCode.NOT_FAMILY_HEAD);
		}

		return family;
	}

	/**
	 * 가족이 없을 경우 예외 처리
	 * @param currentMember 현재 유저
	 */
	private void validateFamilyIsNotNull(Member currentMember) {
		if (currentMember.getFamily() == null) {
			throw new BusinessException(FamilyErrorCode.FAMILY_NOT_EXISTS);
		}
	}

	/**
	 * 가족이 이미 있을 경우 예외 처리
	 * @param currentMember 현재 유저
	 */
	private void validateFamilyIsNull(Member currentMember) {
		if (currentMember.getFamily() != null) {
			throw new BusinessException(FamilyErrorCode.FAMILY_ALREADY_EXISTS);
		}
	}

	/**
	 * 가족 정보 응답 객체 만드는 메서드
	 * @param family 가족
	 * @return 가족 정보 응답 객체
	 */
	private FamilyRes buildFamilyRes(Family family) {

		Member head = family.getHead();

		List<FamilyMemberRes> familyMembers = family.getMembers().stream()
			.filter(member -> !member.equals(head))
			.map(member -> FamilyMemberRes.builder()
				.name(member.getUsername())
				.age(member.getAge())
				.gender(member.getGender())
				.healthState(null)
				.build()
			)
			.toList();

		FamilyMemberRes familyHead = FamilyMemberRes.builder()
			.name(head.getUsername())
			.age(head.getAge())
			.gender(head.getGender())
			.healthState(null)
			.build();

		return FamilyRes.builder()
			.familyName(family.getName())
			.familyHead(familyHead)
			.familyMembers(familyMembers)
			.build();
	}
}
