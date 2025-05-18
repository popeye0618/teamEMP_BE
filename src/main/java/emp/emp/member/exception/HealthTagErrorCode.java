package emp.emp.member.exception;

import org.springframework.http.HttpStatus;

import emp.emp.util.api_response.error_code.ErrorCode;
import lombok.Getter;

@Getter
public enum HealthTagErrorCode implements ErrorCode {
	LENGTH_NOT_VALID("TAG-001", HttpStatus.BAD_REQUEST, "content는 최대 4글자까지 허용됩니다."),
	PUBLIC_COUNT_NOT_VALID("TAG-002", HttpStatus.BAD_REQUEST, "public 태그는 최대 2개까지 생성할 수 있습니다."),
	PRIVATE_COUNT_NOT_VALID("TAG-003", HttpStatus.BAD_REQUEST, "private 태그는 최대 2개까지 생성할 수 있습니다."),
	TAG_NOT_EXIST("TAG-004", HttpStatus.BAD_REQUEST, "존재하지 않는 태그."),
	NOT_MY_TAG("TAG-005", HttpStatus.BAD_REQUEST, "사용자의 태그가 아닙니다."),
	;

	private final String code;
	private final HttpStatus httpStatus;
	private final String message;

	HealthTagErrorCode(String code, HttpStatus httpStatus, String message) {
		this.code = code;
		this.httpStatus = httpStatus;
		this.message = message;
	}
}
