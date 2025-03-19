package emp.emp.auth.oauth2.handler;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import emp.emp.auth.custom.CustomUserDetails;
import emp.emp.util.jwt.JwtTokenProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private final JwtTokenProvider jwtTokenProvider;
	private final StringRedisTemplate redisTemplate;
	private final ObjectMapper objectMapper;

	/*@Value("${redirect-url.frontend.user}")
	private String REDIRECT_URL_USER;

	@Value("${redirect-url.frontend.semi-user}")
	private String REDIRECT_URL_SEMI_USER;*/

	@Value("${redirect-url.frontend.main}")
	private String REDIRECT_URL_MAIN;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException, ServletException {

		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

		String accessToken = jwtTokenProvider.generateAccessToken(userDetails);
		String refreshToken = jwtTokenProvider.generateRefreshToken(userDetails);

		String tempCode = UUID.randomUUID().toString().replace("-", "").substring(0, 16);

		Map<String, String> tokenData = Map.of(
			"accessToken", accessToken,
			"refreshToken", refreshToken
		);
		redisTemplate.opsForValue().set(tempCode, objectMapper.writeValueAsString(tokenData),
			5, TimeUnit.MINUTES);

		getRedirectStrategy().sendRedirect(request, response, REDIRECT_URL_MAIN + "?code=" + tempCode);

		/*String role = userDetails.getAuthorities().iterator().next().getAuthority();
		if ("ROLE_USER".equals(role)) {
			getRedirectStrategy().sendRedirect(request, response, REDIRECT_URL_USER);
		} else if ("ROLE_SEMI_USER".equals(role)) {
			getRedirectStrategy().sendRedirect(request, response, REDIRECT_URL_SEMI_USER);
		}*/
	}
}
