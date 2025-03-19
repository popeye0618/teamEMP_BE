package emp.emp.util.jwt;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.util.ReflectionTestUtils;

import emp.emp.auth.custom.CustomUserDetails;
import emp.emp.auth.dto.LoginDto;
import emp.emp.exception.BusinessException;
import emp.emp.member.entity.Member;
import emp.emp.member.enums.Role;
import emp.emp.member.repository.MemberRepository;
import emp.emp.util.api_response.ErrorCode;
import io.jsonwebtoken.Claims;

class JwtTokenProviderTest {

	private JwtTokenProvider jwtTokenProvider;
	private MemberRepository memberRepository;
	private StringRedisTemplate redisTemplate;

	private final long REFRESH_TOKEN_EXPIRE_TIME = 7 * 24 * 60 * 60 * 1000;

	private final String jwtSecretTest = "MySuperSecretKeyForHS512ThatIsAtLeast64BytesLongAndSuperSafeIndeed!";

	@BeforeEach
	void setUp() {
		memberRepository = Mockito.mock(MemberRepository.class);
		redisTemplate = Mockito.mock(StringRedisTemplate.class);
		jwtTokenProvider = new JwtTokenProvider(memberRepository, redisTemplate);
		ReflectionTestUtils.setField(jwtTokenProvider, "JWT_SECRET", jwtSecretTest);
	}

	@Test
	void testGenerateAndValidateAccessToken() {

		LoginDto loginDto = LoginDto.builder()
			.email("test@example.com")
			.verifyId("TestVerifyId123456")
			.role("ROLE_USER")
			.password("dummy")
			.build();

		CustomUserDetails userDetails = CustomUserDetails.create(loginDto);

		String accessToken = jwtTokenProvider.generateAccessToken(userDetails);
		assertNotNull(accessToken, "Access token should not be null");

		assertTrue(jwtTokenProvider.validateToken(accessToken), "Access token should be valid");

		Claims claims = jwtTokenProvider.getClaims(accessToken);
		assertEquals("TestVerifyId123456", claims.getSubject(), "Subject should match verifyId");
		assertEquals("test@example.com", claims.get("email"), "Email claim should match");
		assertEquals("ROLE_USER", claims.get("role"), "Role claim should match");
	}

	@Test
	void testGenerateAndValidateRefreshToken() {

		LoginDto loginDto = LoginDto.builder()
			.email("test@example.com")
			.verifyId("TestVerifyId123456")
			.role("ROLE_USER")
			.password("dummy")
			.build();
		CustomUserDetails userDetails = CustomUserDetails.create(loginDto);

		ValueOperations<String, String> valueOps = Mockito.mock(ValueOperations.class);
		Mockito.when(redisTemplate.opsForValue()).thenReturn(valueOps);

		String refreshToken = jwtTokenProvider.generateRefreshToken(userDetails);
		assertNotNull(refreshToken, "Refresh token should not be null");
		assertTrue(jwtTokenProvider.validateToken(refreshToken), "Refresh token should be valid");

		Mockito.verify(valueOps).set(userDetails.getName(), refreshToken,
			REFRESH_TOKEN_EXPIRE_TIME, TimeUnit.MILLISECONDS);
	}

	@Test
	void testRefreshTokensSuccess() {

		Member member = Member.builder()
			.email("test@example.com")
			.username("testUser")
			.verifyId("TestVerifyId123456")
			.role(Role.ROLE_USER)
			.build();
		Mockito.when(memberRepository.findByVerifyId("TestVerifyId123456"))
			.thenReturn(Optional.of(member));

		LoginDto loginDto = LoginDto.builder()
			.email("test@example.com")
			.verifyId("TestVerifyId123456")
			.role("ROLE_USER")
			.password("dummy")
			.build();

		CustomUserDetails userDetails = CustomUserDetails.create(loginDto);

		ValueOperations<String, String> valueOps = Mockito.mock(ValueOperations.class);
		Mockito.when(redisTemplate.opsForValue()).thenReturn(valueOps);

		String oldRefreshToken = jwtTokenProvider.generateRefreshToken(userDetails);
		Mockito.reset(valueOps);

		Mockito.when(valueOps.get("TestVerifyId123456")).thenReturn(oldRefreshToken);
		Map<String, String> newTokens = jwtTokenProvider.refreshTokens(oldRefreshToken);

		assertNotNull(newTokens.get("accessToken"), "New access token should not be null");
		assertNotNull(newTokens.get("refreshToken"), "New refresh token should not be null");

		assertTrue(jwtTokenProvider.validateToken(newTokens.get("accessToken")), "New access token should be valid");
		assertTrue(jwtTokenProvider.validateToken(newTokens.get("refreshToken")), "New refresh token should be valid");

		Mockito.verify(redisTemplate).delete("TestVerifyId123456");
		Mockito.verify(valueOps).set("TestVerifyId123456", newTokens.get("refreshToken"),
			REFRESH_TOKEN_EXPIRE_TIME, TimeUnit.MILLISECONDS);
	}

	@Test
	void testRefreshTokensWithInvalidToken() {
		String invalidToken = "invalid.token";
		BusinessException exception = assertThrows(BusinessException.class, () -> {
			jwtTokenProvider.refreshTokens(invalidToken);
		});
		assertEquals(ErrorCode.INVALID_REFRESH_TOKEN, exception.getErrorCode(), "Exception error code should match");
	}
}