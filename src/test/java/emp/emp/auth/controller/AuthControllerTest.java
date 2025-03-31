package emp.emp.auth.controller;

import static org.springframework.test.util.AssertionErrors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import emp.emp.auth.own.dto.LoginRequest;
import emp.emp.auth.own.dto.RegisterRequest;
import emp.emp.member.entity.Member;
import emp.emp.member.enums.Role;
import emp.emp.member.repository.MemberRepository;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private StringRedisTemplate redisTemplate;

	@BeforeEach
	public void setup() {
		redisTemplate.getConnectionFactory().getConnection().flushDb();

		Member member = Member.builder()
			.email("test@example.com")
			.password(passwordEncoder.encode("password123"))
			.username("testuser")
			.verifyId("TestVerifyId123456")
			.role(Role.ROLE_SEMI_USER)
			.provider("EMP")
			.build();
		memberRepository.save(member);
	}

	@AfterEach
	public void clear() {
		redisTemplate.getConnectionFactory().getConnection().flushDb();

		Member member = memberRepository.findByVerifyId("TestVerifyId123456").get();
		memberRepository.delete(member);
	}

	@Test
	@DisplayName("회원 가입 성공 테스트")
	@Transactional
	void testRegisterSuccess() throws Exception {

		RegisterRequest registerRequest = new RegisterRequest();
		registerRequest.setEmail("newuser@example.com");
		registerRequest.setPassword("newpassword12!");

		mockMvc.perform(post("/api/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(registerRequest)))
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("회원가입 이메일 정규표현식 위반 테스트")
	@Transactional
	void testRegisterEmailRegexViolation() throws Exception {
		RegisterRequest registerRequest = new RegisterRequest();
		registerRequest.setEmail("invalid@example");
		registerRequest.setPassword("ValidPW12!");

		mockMvc.perform(post("/api/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(registerRequest)))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.message").value("입력 값이 유효하지 않습니다."));
	}

	@Test
	@DisplayName("회원가입 비밀번호 정규표현식 위반 테스트")
	@Transactional
	void testRegisterPasswordRegexViolation() throws Exception {
		RegisterRequest registerRequest = new RegisterRequest();
		registerRequest.setEmail("invalid@example.com");
		registerRequest.setPassword("invalidPW");  // 조건 위반: 숫자와 특수문자 없음

		mockMvc.perform(post("/api/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(registerRequest)))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.message").value("입력 값이 유효하지 않습니다."));
	}

	@Test
	@DisplayName("로그인 성공 테스트")
	void testLoginSuccess() throws Exception {
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setEmail("test@example.com");
		loginRequest.setPassword("password123");

		mockMvc.perform(post("/api/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(loginRequest)))
			.andExpect(status().is(302))
			.andExpect(header().string("Location", org.hamcrest.Matchers.containsString("?code=")));
	}

	@Test
	@DisplayName("잘못된 비밀번호로 로그인 테스트")
	void testLoginInvalidPassword() throws Exception {
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setEmail("test@example.com");
		loginRequest.setPassword("wrong password");

		mockMvc.perform(post("/api/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(loginRequest)))
			.andExpect(status().is4xxClientError());
	}

	@Test
	@DisplayName("임시 코드로 토큰 발급 성공 테스트")
	@Transactional
	void testTokenExchangeSuccess() throws Exception {
		String tempCode = "temp123456789012"; // 16자리
		Map<String, String> tokenData = Map.of(
			"accessToken", "dummyAccessToken",
			"refreshToken", "dummyRefreshToken"
		);

		String tokenDataJson = objectMapper.writeValueAsString(tokenData);
		redisTemplate.opsForValue().set(tempCode, tokenDataJson, 5, TimeUnit.MINUTES);

		mockMvc.perform(post("/api/token/exchange")
				.param("code", tempCode))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.accessToken").value("dummyAccessToken"))
			.andExpect(jsonPath("$.data.refreshToken").value("dummyRefreshToken"));
	}

	@Test
	@DisplayName("리프레시 토큰 사용 성공 테스트")
	@Transactional
	void testTokenRefreshSuccess() throws Exception {
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setEmail("test@example.com");
		loginRequest.setPassword("password123");

		var loginResult = mockMvc.perform(post("/api/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(loginRequest)))
			.andExpect(status().is(302))
			.andReturn();

		String redirectUrl = loginResult.getResponse().getRedirectedUrl();
		assertNotNull(redirectUrl, "Redirect URL should not be null");

		URI uri = new URI(redirectUrl);
		String query = uri.getQuery();
		String tempCode = query.split("=")[1];
		assertNotNull(tempCode, "Temporary code should not be null");

		MvcResult exchangeResult = mockMvc.perform(post("/api/token/exchange")
				.param("code", tempCode))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.accessToken").exists())
			.andExpect(jsonPath("$.data.refreshToken").exists())
			.andReturn();

		String exchangeResponseContent = exchangeResult.getResponse().getContentAsString();
		Map<String, Object> responseMap = objectMapper.readValue(exchangeResponseContent, Map.class);
		@SuppressWarnings("unchecked")
		Map<String, String> tokens = (Map<String, String>) responseMap.get("data");

		String refreshToken = tokens.get("refreshToken");
		assertNotNull(refreshToken, "Refresh token should not be null");

		// Step 3: /token/refresh 엔드포인트를 호출하여 새 토큰들을 발급받음.
		mockMvc.perform(get("/api/token/refresh")
				.header("Refresh-Token", refreshToken))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.accessToken").exists())
			.andExpect(jsonPath("$.data.refreshToken").exists());
	}

}