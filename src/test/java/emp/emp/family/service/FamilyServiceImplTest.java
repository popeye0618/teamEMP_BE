package emp.emp.family.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import emp.emp.exception.BusinessException;
import emp.emp.family.dto.request.ChangeFamilyNameReq;
import emp.emp.family.dto.request.CreateFamilyReq;
import emp.emp.family.dto.request.JoinFamilyReq;
import emp.emp.family.dto.response.FamilyRes;
import emp.emp.family.entity.Family;
import emp.emp.family.exception.FamilyErrorCode;
import emp.emp.family.repository.FamilyRepository;
import emp.emp.member.entity.Member;
import emp.emp.member.enums.Role;
import emp.emp.util.security.SecurityUtil;

@ExtendWith(MockitoExtension.class)
class FamilyServiceImplTest {

	@Mock
	private FamilyRepository familyRepository;

	@Mock
	private SecurityUtil securityUtil;

	@InjectMocks
	private FamilyServiceImpl familyService;

	private Member member;

	@BeforeEach
	public void setUp() {
		member = Member.builder()
			.provider("testProvider")
			.verifyId("verify123")
			.username("testUser")
			.email("test@example.com")
			.password("password")
			.role(Role.ROLE_USER)
			.gender("MALE")
			.birthDate(LocalDate.of(1990, 1, 1))
			.address("Seoul")
			.build();
	}

	@Test
	@DisplayName("가족 생성 성공")
	void testCreateFamily_Success() {
		when(securityUtil.getCurrentMember()).thenReturn(member);

		CreateFamilyReq req = new CreateFamilyReq();
		req.setName("New Family");

		familyService.createFamily(req);

		verify(familyRepository, times(1)).save(any(Family.class));
	}

	@Test
	@DisplayName("이미 가족에 속해있는 경우 가족 생성 실패")
	void testCreateFamily_AlreadyExists() {
		Family existingFamily = Family.builder()
			.name("Existing Family")
			.head(member)
			.build();
		member.setFamily(existingFamily);

		when(securityUtil.getCurrentMember()).thenReturn(member);

		CreateFamilyReq req = new CreateFamilyReq();
		req.setName("New Family");

		BusinessException ex = assertThrows(BusinessException.class, () -> familyService.createFamily(req));
		assertThat(ex.getMessage()).isEqualTo(FamilyErrorCode.FAMILY_ALREADY_EXISTS.getMessage());
	}

	@Test
	@DisplayName("가족 가입 성공")
	void testJoinFamily_Success() {
		when(securityUtil.getCurrentMember()).thenReturn(member);

		Member headMember = Member.builder()
			.provider("provider")
			.verifyId("headId")
			.username("headUser")
			.email("head@example.com")
			.password("password")
			.role(Role.ROLE_USER)
			.gender("MALE")
			.birthDate(LocalDate.of(1990, 2, 2))
			.address("Busan")
			.build();

		Family family = Family.builder()
			.name("Family")
			.head(headMember)
			.build();
		String familyCode = family.getCode();

		when(familyRepository.findByCode(familyCode)).thenReturn(Optional.of(family));

		JoinFamilyReq req = new JoinFamilyReq();
		req.setCode(familyCode);

		familyService.joinFamily(req);

		assertThat(member.getFamily()).isEqualTo(family);
	}

	@Test
	@DisplayName("잘못된 코드로 가족 가입 시도")
	void testJoinFamily_InvalidFamilyCode() {
		when(securityUtil.getCurrentMember()).thenReturn(member);

		String invalidCode = "invalid";
		when(familyRepository.findByCode(invalidCode)).thenReturn(Optional.empty());

		JoinFamilyReq req = new JoinFamilyReq();
		req.setCode(invalidCode);

		BusinessException ex = assertThrows(BusinessException.class, () -> familyService.joinFamily(req));
		assertThat(ex.getMessage()).isEqualTo(FamilyErrorCode.INVALID_FAMILY_CODE.getMessage());
	}

	@Test
	@DisplayName("가족이 있는 상태로 다른 가족 가입 요청")
	void testJoinFamily_AlreadyExists() {
		Family existingFamily = Family.builder()
			.name("Existing Family")
			.head(member)
			.build();
		member.setFamily(existingFamily);

		when(securityUtil.getCurrentMember()).thenReturn(member);

		JoinFamilyReq req = new JoinFamilyReq();
		req.setCode("someCode");

		BusinessException ex = assertThrows(BusinessException.class, () -> familyService.joinFamily(req));
		assertThat(ex.getMessage()).isEqualTo(FamilyErrorCode.FAMILY_ALREADY_EXISTS.getMessage());
	}

	@Test
	@DisplayName("가족 삭제 성공")
	public void testDeleteFamily_Success() {
		Family family = Family.builder()
			.name("Family")
			.head(member)
			.build();
		family.addMember(member);
		member.setFamily(family);

		when(securityUtil.getCurrentMember()).thenReturn(member);

		familyService.deleteFamily();

		verify(familyRepository, times(1)).delete(family);
		assertNull(member.getFamily());
	}

	@Test
	@DisplayName("가족이 없는데 가족 삭제 요청")
	public void testDeleteFamily_NotExists() {
		when(securityUtil.getCurrentMember()).thenReturn(member);

		BusinessException ex = assertThrows(BusinessException.class, () -> familyService.deleteFamily());
		assertThat(ex.getMessage()).isEqualTo(FamilyErrorCode.FAMILY_NOT_EXISTS.getMessage());
	}

	@Test
	@DisplayName("head가 아닌 구성원의 가족 삭제 요청")
	public void testDeleteFamily_NotFamilyHead() {
		Member headMember = Member.builder()
			.provider("provider")
			.verifyId("headId")
			.username("headUser")
			.email("head@example.com")
			.password("password")
			.role(Role.ROLE_USER)
			.gender("MALE")
			.birthDate(LocalDate.of(1990, 2, 2))
			.address("Busan")
			.build();

		Family family = Family.builder()
			.name("Family")
			.head(headMember)
			.build();
		family.addMember(member);
		member.setFamily(family);

		when(securityUtil.getCurrentMember()).thenReturn(member);

		BusinessException ex = assertThrows(BusinessException.class, () -> familyService.deleteFamily());
		assertThat(ex.getMessage()).isEqualTo(FamilyErrorCode.NOT_FAMILY_HEAD.getMessage());
	}

	@Test
	@DisplayName("가족 이름 수정 성공: head인 경우")
	void testChangeFamilyName_Success() {
		// 현재 유저(member)가 가족 head인 경우
		Family family = Family.builder()
			.name("Old Family Name")
			.head(member)
			.build();
		family.addMember(member);
		member.setFamily(family);
		when(securityUtil.getCurrentMember()).thenReturn(member);

		// 요청 객체에 새 가족 이름 설정
		ChangeFamilyNameReq req = new ChangeFamilyNameReq();
		req.setName("New Family Name");

		// 메서드 실행
		familyService.changeFamilyName(req);

		// 가족 이름이 변경되었는지 검증
		assertEquals("New Family Name", family.getName());
	}

	@Test
	@DisplayName("가족 이름 수정 실패: head가 아닌 경우")
	void testChangeFamilyName_NotFamilyHead() {
		// head가 아닌 다른 구성원인 경우 예외 발생해야 함
		Member headMember = Member.builder()
			.provider("provider")
			.verifyId("headId")
			.username("headUser")
			.email("head@example.com")
			.password("password")
			.role(Role.ROLE_USER)
			.gender("MALE")
			.birthDate(LocalDate.of(1990, 2, 2))
			.address("Busan")
			.build();

		Family family = Family.builder()
			.name("Family")
			.head(headMember)
			.build();
		family.addMember(headMember);
		family.addMember(member);
		// 현재 유저(member)는 head가 아니므로 가족 설정
		member.setFamily(family);

		when(securityUtil.getCurrentMember()).thenReturn(member);

		ChangeFamilyNameReq req = new ChangeFamilyNameReq();
		req.setName("New Family Name");

		// 예외가 발생하는지 검증
		BusinessException ex = assertThrows(BusinessException.class, () -> familyService.changeFamilyName(req));
		assertThat(ex.getMessage()).isEqualTo(FamilyErrorCode.NOT_FAMILY_HEAD.getMessage());
	}

	@Test
	@DisplayName("가족 탈퇴 성공: head가 아닌 구성원 탈퇴")
	void testExitFamily_Success() {
		// head가 아닌 구성원(member)이 가족 탈퇴하는 경우
		Member headMember = Member.builder()
			.provider("provider")
			.verifyId("headId")
			.username("headUser")
			.email("head@example.com")
			.password("password")
			.role(Role.ROLE_USER)
			.gender("MALE")
			.birthDate(LocalDate.of(1990, 2, 2))
			.address("Busan")
			.build();

		Family family = Family.builder()
			.name("Family")
			.head(headMember)
			.build();
		family.addMember(headMember);
		family.addMember(member);
		// 현재 유저(member)는 head가 아니므로 가족 설정
		member.setFamily(family);

		when(securityUtil.getCurrentMember()).thenReturn(member);

		// 가족 탈퇴 메서드 실행
		familyService.exitFamily();

		// 탈퇴 후 member의 가족 정보가 null이어야 함
		assertNull(member.getFamily());
	}

	@Test
	@DisplayName("가족 탈퇴 실패: head는 탈퇴 불가능")
	void testExitFamily_HeadCannotExit() {
		// 가족 head(member)는 탈퇴할 수 없어 예외 발생해야 함
		Family family = Family.builder()
			.name("Family")
			.head(member)
			.build();
		family.addMember(member);
		member.setFamily(family);

		when(securityUtil.getCurrentMember()).thenReturn(member);

		BusinessException ex = assertThrows(BusinessException.class, () -> familyService.exitFamily());
		assertThat(ex.getMessage()).isEqualTo(FamilyErrorCode.CANT_EXIT_FAMILY.getMessage());
	}

	@Test
	@DisplayName("가족 탈퇴 실패: 가족이 없는 경우")
	void testExitFamily_FamilyNotExists() {
		// 가족 정보가 없는 경우 예외 발생
		when(securityUtil.getCurrentMember()).thenReturn(member);

		BusinessException ex = assertThrows(BusinessException.class, () -> familyService.exitFamily());
		assertThat(ex.getMessage()).isEqualTo(FamilyErrorCode.FAMILY_NOT_EXISTS.getMessage());
	}

	@Test
	@DisplayName("가족 정보 조회 성공")
	void testGetFamilyInfo_Success() {
		// 가족 정보 조회 성공 테스트
		// 현재 유저(member)가 head로 설정됨
		Family family = Family.builder()
			.name("Family Name")
			.head(member)
			.build();
		family.addMember(member);

		// 추가 구성원 생성 후 가족에 추가
		Member member2 = Member.builder()
			.provider("provider2")
			.verifyId("verify456")
			.username("secondUser")
			.email("second@example.com")
			.password("password")
			.role(Role.ROLE_USER)
			.gender("FEMALE")
			.birthDate(LocalDate.of(1992, 3, 3))
			.address("Incheon")
			.build();
		family.addMember(member2);

		member.setFamily(family);

		when(securityUtil.getCurrentMember()).thenReturn(member);

		FamilyRes res = familyService.getFamilyInfo();

		// 가족 이름, head, 구성원 리스트의 크기 등을 검증
		assertEquals("Family Name", res.getFamilyName());
		assertEquals(member.getUsername(), res.getFamilyHead().getName());
		assertEquals(family.getMembers().size(), res.getFamilyMembers().size());
	}

	@Test
	@DisplayName("가족 정보 조회 실패: 가족이 없는 경우")
	void testGetFamilyInfo_FamilyNotExists() {
		// 가족 정보가 없을 경우 예외 발생 검증
		when(securityUtil.getCurrentMember()).thenReturn(member);

		BusinessException ex = assertThrows(BusinessException.class, () -> familyService.getFamilyInfo());
		assertThat(ex.getMessage()).isEqualTo(FamilyErrorCode.FAMILY_NOT_EXISTS.getMessage());
	}
}