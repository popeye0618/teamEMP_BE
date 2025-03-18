package emp.emp.util.jwt;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import emp.emp.auth.custom.CustomUserDetails;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

	// 액세스 토큰 만료 시간
	private final long ACCESS_TOKEN_EXPIRE_TIME = 60 * 60 * 1000;
	// 리프레시 토큰 만료 시간
	private final long REFRESH_TOKEN_EXPIRE_TIME = 7 * 24 * 60 * 60 * 1000;
	@Value("${jwt.secret}")
	private String JWT_SECRET;

	public static Key getKeyFromString(String secret) {
		return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
	}

	/**
	 * 액세스 토큰 생성
	 *
	 * @param userDetails 인증된 사용자 정보
	 * @return JWT 액세스 토큰 문자열
	 */
	public String generateAccessToken(CustomUserDetails userDetails) {
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + ACCESS_TOKEN_EXPIRE_TIME);
		Key key = getKeyFromString(JWT_SECRET);

		return Jwts.builder()
			.setSubject(String.valueOf(userDetails.getName()))
			.setIssuedAt(now)
			.setExpiration(expiryDate)
			.claim("email", userDetails.getEmail())
			.claim("role", userDetails.getAuthorities())
			.signWith(key, SignatureAlgorithm.HS512)
			.compact();
	}

	/**
	 * 리프레시 토큰 생성
	 *
	 * @param userDetails 인증된 사용자 정보
	 * @return JWT 리프레시 토큰 문자열
	 */
	public String generateRefreshToken(CustomUserDetails userDetails) {
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + REFRESH_TOKEN_EXPIRE_TIME);
		Key key = getKeyFromString(JWT_SECRET);

		return Jwts.builder()
			.setSubject(String.valueOf(userDetails.getName()))
			.setIssuedAt(now)
			.setExpiration(expiryDate)
			.signWith(key, SignatureAlgorithm.HS512)
			.compact();
	}
}
