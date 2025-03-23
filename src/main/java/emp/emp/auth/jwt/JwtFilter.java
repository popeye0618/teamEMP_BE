package emp.emp.auth.jwt;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import emp.emp.auth.custom.CustomUserDetails;
import emp.emp.auth.exception.AuthErrorCode;
import emp.emp.util.api_response.Response;
import emp.emp.util.jwt.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

	private static final List<String> WHITELIST = List.of(
		"/api/register",
		"/api/login",
		"/api/token/**"
	);
	private final JwtTokenProvider jwtTokenProvider;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		log.info("requestURI={}", request.getRequestURI());
		if (WHITELIST.contains(request.getRequestURI())) {
			filterChain.doFilter(request, response);
			return;
		}

		String accessToken = resolveToken(request);

		if (accessToken != null) {
			try {
				if (jwtTokenProvider.validateToken(accessToken)) {
					Claims claims = jwtTokenProvider.getClaims(accessToken);
					CustomUserDetails userDetails = CustomUserDetails.createCustomUserDetailsFromClaims(claims);

					UsernamePasswordAuthenticationToken authentication =
						new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
					SecurityContextHolder.getContext().setAuthentication(authentication);

				} else {
					sendTokenRefreshResponse(response);
					return;
				}
			} catch (Exception e) {
				sendTokenRefreshResponse(response);
				return;
			}
		}
		filterChain.doFilter(request, response);
	}

	private String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}

	private void sendTokenRefreshResponse(HttpServletResponse response) throws IOException {
		response.setContentType("application/json;charset=UTF-8");
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		Response<Void> errorResponse = Response.errorResponse(AuthErrorCode.INVALID_ACCESS_TOKEN);
		response.getWriter().write(errorResponse.convertToJson());
	}
}
