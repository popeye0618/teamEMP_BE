package emp.emp.auth.own.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import emp.emp.auth.custom.CustomUserDetails;
import emp.emp.auth.own.dto.RegisterRequest;
import emp.emp.exception.BusinessException;
import emp.emp.member.entity.Member;
import emp.emp.member.enums.Role;
import emp.emp.member.repository.MemberRepository;
import emp.emp.util.api_response.ErrorCode;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

	@Mock
	private MemberRepository memberRepository;

	@Mock
	private PasswordEncoder passwordEncoder;

	@InjectMocks
	private CustomUserDetailsService customUserDetailsService;

	private Member testMember;

	@BeforeEach
	void setUp() {
		testMember = Member.builder()
			.email("test@example.com")
			.username("testUser")
			.password("encodedPassword")
			.verifyId("TestVerifyId123456") // 예시 verifyId
			.role(Role.ROLE_SEMI_USER)
			.provider("EMP")
			.build();
	}

	@Test
	@DisplayName("유저 불러오기 성공 테스트")
	void loadUserByUsername_Success() {
		when(memberRepository.findByEmail("test@example.com"))
			.thenReturn(Optional.of(testMember));

		UserDetails userDetails = customUserDetailsService.loadUserByUsername("test@example.com");

		assertNotNull(userDetails);
		assertTrue(userDetails instanceof CustomUserDetails);
		CustomUserDetails customDetails = (CustomUserDetails) userDetails;
		assertEquals("test@example.com", customDetails.getEmail());
	}

	@Test
	@DisplayName("유저 불러오기 실패 테스트")
	void loadUserByUsername_NotFound() {
		when(memberRepository.findByEmail("nonexistent@example.com"))
			.thenReturn(Optional.empty());

		BusinessException exception = assertThrows(BusinessException.class, () -> {
			customUserDetailsService.loadUserByUsername("nonexistent@example.com");
		});
		assertEquals(ErrorCode.USER_NOT_FOUND, exception.getErrorCode());
	}

	@Test
	@DisplayName("회원가입 성공 테스트")
	void register_Success() {
		RegisterRequest request = new RegisterRequest();
		request.setEmail("newuser@example.com");
		request.setPassword("newPassword");

		when(memberRepository.existsByEmail("newuser@example.com")).thenReturn(false);
		when(passwordEncoder.encode("newPassword")).thenReturn("encodedNewPassword");

		customUserDetailsService.register(request);

		verify(memberRepository, times(1)).save(any(Member.class));
	}

	@Test
	@DisplayName("회원가입 시 이메일 중복 테스트")
	void register_EmailDuplicated() {
		RegisterRequest request = new RegisterRequest();
		request.setEmail("test@example.com");
		request.setPassword("password");

		// 이미 이메일 존재
		when(memberRepository.existsByEmail("test@example.com")).thenReturn(true);

		BusinessException exception = assertThrows(BusinessException.class, () -> {
			customUserDetailsService.register(request);
		});
		assertEquals(ErrorCode.EMAIL_DUPLICATED, exception.getErrorCode());
	}

}