package emp.emp.auth.oauth2.provider;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OAuth2Provider {
	KAKAO("kakao"),
	NAVER("naver"),
	GOOGLE("google");

	private final String registrationId;
}
