package emp.emp.member.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import emp.emp.auth.custom.CustomUserDetails;
import emp.emp.auth.exception.AuthErrorCode;
import emp.emp.exception.BusinessException;
import emp.emp.member.dto.request.InputFeatureReq;
import emp.emp.member.dto.response.InputFeatureRes;
import emp.emp.member.entity.Member;
import emp.emp.member.enums.Role;
import emp.emp.member.repository.MemberRepository;
import emp.emp.util.jwt.JwtTokenProvider;
import emp.emp.util.security.SecurityUtil;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

	private final MemberRepository memberRepository;
	private final SecurityUtil securityUtil;
	private final JwtTokenProvider jwtTokenProvider;

	/**
	 * 유저 피처 입력
	 *
	 * @param userDetails 로그인된 유저
	 * @param request     유저 정보
	 * @return AT, RT
	 */
	@Override
	@Transactional
	public InputFeatureRes inputFeature(CustomUserDetails userDetails, InputFeatureReq request) {
		Member currentMember = securityUtil.getCurrentMember();

		if (currentMember.getRole().equals(Role.ROLE_USER)) {
			throw new BusinessException(AuthErrorCode.INVALID_ROLE);
		}

		inputUserInfo(currentMember, request);

		jwtTokenProvider.deleteRefreshToken(currentMember.getVerifyId());

		String accessToken = jwtTokenProvider.generateAccessToken(userDetails);
		String refreshToken = jwtTokenProvider.generateRefreshToken(userDetails);

		return InputFeatureRes.builder()
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.build();
	}

	private void inputUserInfo(Member member, InputFeatureReq request) {
		member.setUsername(request.getUsername());
		member.setGender(request.getGender());
		member.setBirthDay(request.getBirthday());
		member.setAddress(request.getAddress());
		member.setRole(Role.ROLE_USER);
	}
}
