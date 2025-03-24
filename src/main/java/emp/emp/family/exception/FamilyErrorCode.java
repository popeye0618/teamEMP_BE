package emp.emp.family.exception;

import org.springframework.http.HttpStatus;

import emp.emp.util.api_response.error_code.ErrorCode;
import lombok.Getter;

@Getter
public enum FamilyErrorCode implements ErrorCode {
	FAMILY_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "이미 가족에 속해있는 유저"),
	FAMILY_NOT_EXISTS(HttpStatus.BAD_REQUEST, "존재하지 않는 가족"),
	INVALID_FAMILY_CODE(HttpStatus.BAD_REQUEST, "잘못된 가족 코드"),
	NOT_FAMILY_HEAD(HttpStatus.BAD_REQUEST, "가족의 head가 아닙니다"),
	CANT_EXIT_FAMILY(HttpStatus.BAD_REQUEST, "가족의 head는 탈퇴할 수 없습니다");

	private final HttpStatus httpStatus;
	private final String message;

	FamilyErrorCode(HttpStatus httpStatus, String message) {
		this.httpStatus = httpStatus;
		this.message = message;
	}
}
