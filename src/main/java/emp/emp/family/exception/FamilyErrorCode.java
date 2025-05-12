package emp.emp.family.exception;

import org.springframework.http.HttpStatus;

import emp.emp.util.api_response.error_code.ErrorCode;
import lombok.Getter;

@Getter
public enum FamilyErrorCode implements ErrorCode {
	FAMILY_ALREADY_EXISTS("FAM-001", HttpStatus.BAD_REQUEST, "이미 가족에 속해있는 유저"),
	FAMILY_NOT_EXISTS("FAM-002", HttpStatus.BAD_REQUEST, "존재하지 않는 가족"),
	INVALID_FAMILY_CODE("FAM-003", HttpStatus.BAD_REQUEST, "잘못된 가족 코드"),
	NOT_FAMILY_HEAD("FAM-004", HttpStatus.BAD_REQUEST, "가족의 head가 아닙니다"),
	CANT_EXIT_FAMILY("FAM-005", HttpStatus.BAD_REQUEST, "가족의 head는 탈퇴할 수 없습니다");

	private final String code;
	private final HttpStatus httpStatus;
	private final String message;

	FamilyErrorCode(String code, HttpStatus httpStatus, String message) {
		this.code = code;
		this.httpStatus = httpStatus;
		this.message = message;
	}
}
