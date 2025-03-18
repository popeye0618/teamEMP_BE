package emp.emp.auth.custom;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import emp.emp.auth.dto.LoginDto;

public class CustomUserDetails implements UserDetails, OAuth2User {

	private final LoginDto loginDto;

	// OAuth2 로그인 시 제공되는 추가 속성들
	private Map<String, Object> attributes;

	public CustomUserDetails(LoginDto loginDto) {
		this.loginDto = loginDto;
	}

	/**
	 * 자체 로그인 시 사용할 팩토리 메서드
	 *
	 * @param loginDto 로그인에 필요한 사용자 정보
	 * @return CustomUserDetails 객체
	 */
	public static CustomUserDetails create(LoginDto loginDto) {
		return new CustomUserDetails(loginDto);
	}

	/**
	 * OAuth2 로그인 시 사용할 팩토리 메서드
	 *
	 * @param loginDto   로그인에 필요한 사용자 정보
	 * @param attributes OAuth2 제공자로부터 받은 속성들
	 * @return CustomUserDetails 객체
	 */
	public static CustomUserDetails create(LoginDto loginDto, Map<String, Object> attributes) {
		CustomUserDetails userDetails = new CustomUserDetails(loginDto);
		userDetails.setAttributes(attributes);
		return userDetails;
	}

	public String getEmail() {
		return loginDto.getEmail();
	}

	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	/**
	 * OAuth2 속성들을 설정
	 *
	 * @param attributes OAuth2 제공자로부터 받은 속성들
	 */
	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	@Override
	public String getName() {
		return String.valueOf(loginDto.getVerifyId());
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singleton(new SimpleGrantedAuthority(loginDto.getRole()));
	}

	@Override
	public String getPassword() {
		return loginDto.getPassword();
	}

	@Override
	public String getUsername() {
		return loginDto.getVerifyId();
	}
}
