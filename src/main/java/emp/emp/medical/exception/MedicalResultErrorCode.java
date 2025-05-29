package emp.emp.medical.exception;

import org.springframework.http.HttpStatus;

import emp.emp.util.api_response.error_code.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MedicalResultErrorCode implements ErrorCode {

  // 공통 오류
  MEDICAL_RESULT_NOT_FOUND(HttpStatus.NOT_FOUND, "MED001", "진료 결과를 찾을 수 없습니다."),
  ACCESS_DENIED(HttpStatus.FORBIDDEN, "MED002", "이 진료 결과에 접근할 권한이 없습니다."),
  INVALID_MEDICAL_RESULT_DATA(HttpStatus.BAD_REQUEST, "MED003", "진료 결과 데이터가 유효하지 않습니다."),

  // 이미지 관련 오류
  IMAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "MED004", "이미지를 찾을 수 없습니다."),
  INVALID_IMAGE_FORMAT(HttpStatus.BAD_REQUEST, "MED005", "지원하지 않는 이미지 형식입니다."),
  IMAGE_UPLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "MED006", "이미지 업로드에 실패했습니다."),

  // 캘린더 이벤트 관련 오류
  CALENDAR_EVENT_NOT_FOUND(HttpStatus.NOT_FOUND, "MED007", "연결할 캘린더 이벤트를 찾을 수 없습니다."),
  EVENT_TYPE_INVALID(HttpStatus.BAD_REQUEST, "MED008", "진료 결과는 진료 결과 일정에만 등록할 수 있습니다."),
  MEDICAL_RESULT_ALREADY_EXISTS(HttpStatus.CONFLICT, "MED009", "해당 일정에 이미 진료 결과가 등록되어 있습니다."),

  // 가족 공유 관련 오류
  FAMILY_NOT_FOUND(HttpStatus.NOT_FOUND, "MED010", "가족 정보를 찾을 수 없습니다."),

  // 데이터베이스 오류
  DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "MED011", "데이터베이스 처리 중 오류가 발생했습니다.");

  private final HttpStatus httpStatus;
  private final String code;
  private final String message;
}