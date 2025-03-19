package emp.emp.util.jwt;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import emp.emp.auth.custom.CustomUserDetails;
import emp.emp.exception.BusinessException;
import emp.emp.member.entity.Member;
import emp.emp.member.repository.MemberRepository;
import emp.emp.util.api_response.ErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

	private final MemberRepository memberRepository;
	private final StringRedisTemplate redisTemplate;

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
			.claim("role", userDetails.getAuthorities().iterator().next().getAuthority())
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

		String refreshToken = Jwts.builder()
			.setSubject(String.valueOf(userDetails.getName()))
			.setIssuedAt(now)
			.setExpiration(expiryDate)
			.signWith(key, SignatureAlgorithm.HS512)
			.compact();

		// 키: verifyId, 값: refreshToken
		redisTemplate.opsForValue().set(userDetails.getName(), refreshToken, REFRESH_TOKEN_EXPIRE_TIME, TimeUnit.MILLISECONDS);

		return refreshToken;
	}

	/**
	 * 리프레시 토큰을 이용해 새 토큰 발급 (토큰 로테이션)
	 * @param refreshToken 리프레시 토큰
	 * @return AccessToken, RefreshToken
	 */
	public Map<String, String> refreshTokens(String refreshToken) {

		if (!validateToken(refreshToken)) {
			throw new BusinessException(ErrorCode.INVALID_REFRESH_TOKEN);
		}

		String verifyId = getClaims(refreshToken).getSubject();

		String storedRefreshToken = redisTemplate.opsForValue().get(verifyId);
		if (!storedRefreshToken.equals(refreshToken)) {
			throw new BusinessException(ErrorCode.INVALID_REFRESH_TOKEN);
		}

		redisTemplate.delete(verifyId);


		Date now = new Date();
		Date accessExpiryDate = new Date(now.getTime() + ACCESS_TOKEN_EXPIRE_TIME);
		Date refreshExpiryDate = new Date(now.getTime() + REFRESH_TOKEN_EXPIRE_TIME);
		Key key = getKeyFromString(JWT_SECRET);
		Member member = memberRepository.findByVerifyId(verifyId)
			.orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

		String newAccessToken = Jwts.builder()
			.setSubject(verifyId)
			.setIssuedAt(now)
			.setExpiration(accessExpiryDate)
			.claim("email", member.getEmail())
			.claim("role", member.getRole())
			.signWith(key, SignatureAlgorithm.HS512)
			.compact();

		String newRefreshToken = Jwts.builder()
			.setSubject(verifyId)
			.setIssuedAt(now)
			.setExpiration(refreshExpiryDate)
			.signWith(key, SignatureAlgorithm.HS512)
			.compact();

		redisTemplate.opsForValue().set(verifyId, newRefreshToken, REFRESH_TOKEN_EXPIRE_TIME, TimeUnit.MILLISECONDS);

		return Map.of(
			"accessToken", newAccessToken,
			"refreshToken", newRefreshToken
		);
	}

	public boolean validateToken(String token) {
		try {
			Key key = getKeyFromString(JWT_SECRET);
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public Claims getClaims(String token) {
		Key key = getKeyFromString(JWT_SECRET);
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
	}
}
