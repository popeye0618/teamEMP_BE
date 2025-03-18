package emp.emp.auth.oauth2.provider;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.user.OAuth2User;

import emp.emp.auth.oauth2.provider.google.GoogleUser;
import emp.emp.auth.oauth2.provider.kakao.KakaoUser;
import emp.emp.auth.oauth2.provider.naver.NaverUser;
import emp.emp.exception.BusinessException;
import emp.emp.util.api_response.ErrorCode;

public class OAuth2ProviderFactory {
	public static OAuth2ProviderUser getOAuth2UserInfo(ClientRegistration clientRegistration, OAuth2User oAuth2User) {

		String registrationId = clientRegistration.getRegistrationId();

		if (registrationId.equals(OAuth2Provider.KAKAO.getRegistrationId())) {
			return new KakaoUser(oAuth2User, clientRegistration);
		} else if (registrationId.equals(OAuth2Provider.NAVER.getRegistrationId())) {
			return new NaverUser(oAuth2User, clientRegistration);
		} else if (registrationId.equals(OAuth2Provider.GOOGLE.getRegistrationId())) {
			return new GoogleUser(oAuth2User, clientRegistration);
		} else {
			throw new BusinessException(ErrorCode.INVALID_OAUTH2_PROVIDER);
		}
	}
}
