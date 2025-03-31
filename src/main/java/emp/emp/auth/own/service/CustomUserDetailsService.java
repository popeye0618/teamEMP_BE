package emp.emp.auth.own.service;

import java.util.UUID;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import emp.emp.auth.custom.CustomUserDetails;
import emp.emp.auth.dto.LoginDto;
import emp.emp.auth.exception.AuthErrorCode;
import emp.emp.auth.own.dto.RegisterRequest;
import emp.emp.exception.BusinessException;
import emp.emp.member.entity.Member;
import emp.emp.member.enums.Role;
import emp.emp.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements AuthService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	/**
	 * UserDetailsService 구현 메서드
	 *
	 * @param email the username identifying the user whose data is required.
	 * @return CustomUserDetails
	 * @throws UsernameNotFoundException UserDetailsService 예외 처리
	 */
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Member member = memberRepository.findByEmail(email)
			.orElseThrow(() -> new BusinessException(AuthErrorCode.USER_NOT_FOUND));

		LoginDto loginDto = LoginDto.builder()
			.verifyId(member.getVerifyId())
			.username(member.getUsername())
			.email(member.getEmail())
			.role(member.getRole().name())
			.password(member.getPassword())
			.build();

		return CustomUserDetails.create(loginDto);
	}

	/**
	 * 사용자 회원가입
	 *
	 * @param request 회원가입을 위한 정보
	 */
	@Override
	@Transactional
	public void register(RegisterRequest request) {

		if (memberRepository.existsByEmail(request.getEmail())) {
			throw new BusinessException(AuthErrorCode.EMAIL_DUPLICATED);
		}

		String verifyId = UUID.randomUUID().toString().replace("-", "").substring(0, 16);

		Member member = Member.builder()
			.email(request.getEmail())
			.password(passwordEncoder.encode(request.getPassword()))
			.role(Role.ROLE_SEMI_USER)
			.verifyId(verifyId)
			.provider("EMP")
			.build();

		memberRepository.save(member);
	}
}
