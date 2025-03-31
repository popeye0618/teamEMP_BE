package emp.emp.member.service;

import static org.assertj.core.api.AssertionsForInterfaceTypes.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import emp.emp.auth.custom.CustomUserDetails;
import emp.emp.auth.dto.LoginDto;
import emp.emp.member.dto.request.InputFeatureReq;
import emp.emp.member.dto.response.InputFeatureRes;
import emp.emp.member.entity.Member;
import emp.emp.member.repository.MemberRepository;
import emp.emp.util.jwt.JwtTokenProvider;
import emp.emp.util.security.SecurityUtil;

@ExtendWith(MockitoExtension.class)
class MemberServiceImplTest {

	@Mock
	private MemberRepository memberRepository;

	@Mock
	private SecurityUtil securityUtil;

	@Mock
	private JwtTokenProvider jwtTokenProvider;

	@InjectMocks
	private MemberServiceImpl memberService;

	private Member dummyMember;
	private CustomUserDetails customUserDetails;

	@BeforeEach
	void setUp() {
		dummyMember = Member.builder().build();

		// CustomUserDetails 생성용 LoginDto 객체 생성 (빌더 사용)
		LoginDto loginDto = LoginDto.builder()
			.verifyId("user123")
			.email("user@example.com")
			.role("ROLE_USER")
			.password("password")
			.build();
		customUserDetails = CustomUserDetails.create(loginDto);
	}

	@Test
	void testInputFeature() {
		// given : 입력 Feature 요청 DTO 설정
		InputFeatureReq request = new InputFeatureReq();
		request.setUsername("newUsername");
		request.setGender("Male");
		request.setBirthday(LocalDate.of(1990, 1, 1));
		request.setAddress("Seoul");

		// securityUtil.getCurrentMember()가 dummyMember를 반환하도록 설정
		when(securityUtil.getCurrentMember()).thenReturn(dummyMember);
		// JwtTokenProvider가 토큰 문자열을 반환하도록 stub 처리
		when(jwtTokenProvider.generateAccessToken(customUserDetails)).thenReturn("dummyAccessToken");
		when(jwtTokenProvider.generateRefreshToken(customUserDetails)).thenReturn("dummyRefreshToken");

		// when : MemberService의 inputFeature 호출
		InputFeatureRes result = memberService.inputFeature(customUserDetails, request);

		// then : dummyMember의 필드가 요청에 맞게 업데이트 되었는지 확인
		assertThat(dummyMember.getUsername()).isEqualTo("newUsername");
		assertThat(dummyMember.getGender()).isEqualTo("Male");
		assertThat(dummyMember.getBirthDay()).isEqualTo(LocalDate.of(1990, 1, 1));
		assertThat(dummyMember.getAddress()).isEqualTo("Seoul");

		// then : 반환된 토큰 값 검증
		assertThat(result.getAccessToken()).isEqualTo("dummyAccessToken");
		assertThat(result.getRefreshToken()).isEqualTo("dummyRefreshToken");
	}

}