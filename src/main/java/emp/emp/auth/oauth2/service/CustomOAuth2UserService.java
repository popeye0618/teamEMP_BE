package emp.emp.auth.oauth2.service;

import java.util.Optional;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import emp.emp.auth.custom.CustomUserDetails;
import emp.emp.auth.dto.LoginDto;
import emp.emp.auth.oauth2.provider.OAuth2ProviderFactory;
import emp.emp.auth.oauth2.provider.OAuth2ProviderUser;
import emp.emp.member.entity.Member;
import emp.emp.member.enums.Role;
import emp.emp.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

	private final MemberRepository memberRepository;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = super.loadUser(userRequest);

		return processOAuth2User(userRequest, oAuth2User);
	}

	private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
		ClientRegistration clientRegistration = userRequest.getClientRegistration();
		OAuth2ProviderUser oAuth2UserInfo = OAuth2ProviderFactory.getOAuth2UserInfo(clientRegistration, oAuth2User);

		Optional<Member> memberOpt = memberRepository.findByVerifyId(oAuth2UserInfo.getVerifyId());
		Member member = memberOpt.orElseGet(() -> register(oAuth2UserInfo));

		LoginDto loginDto = LoginDto.builder()
			.email(member.getEmail())
			.verifyId(member.getVerifyId())
			.role(member.getRole().name())
			.build();

		return CustomUserDetails.create(loginDto, oAuth2User.getAttributes());
	}

	private Member register(OAuth2ProviderUser userInfo) {
		Member newMember = Member.builder()
			.provider(userInfo.getProvider())
			.email(userInfo.getEmail())
			.verifyId(userInfo.getVerifyId())
			.role(Role.ROLE_SEMI_USER)
			.build();

		memberRepository.save(newMember);

		return newMember;
	}
}
