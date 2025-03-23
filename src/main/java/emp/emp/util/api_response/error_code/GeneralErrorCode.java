package emp.emp.util.api_response.error_code;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum GeneralErrorCode implements ErrorCode {
	BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청"),
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러");

	private final HttpStatus httpStatus;
	private final String message;

	GeneralErrorCode(HttpStatus httpStatus, String message) {
		this.httpStatus = httpStatus;
		this.message = message;
	}
}
