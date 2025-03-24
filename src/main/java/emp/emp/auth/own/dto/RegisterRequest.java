package emp.emp.auth.own.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

	@Email(
		regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
		message = "유효한 이메일 주소를 입력해 주세요."
	)
	private String email;

	@Pattern(
		regexp = "^(?=.*\\d)(?=.*[^a-zA-Z0-9]).{8,}$",
		message = "비밀번호는 8자 이상, 숫자와 특수문자를 포함해야 합니다."
	)
	private String password;
}
