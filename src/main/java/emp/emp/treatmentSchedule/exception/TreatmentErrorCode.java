package emp.emp.treatmentSchedule.exception;

import org.springframework.http.HttpStatus;

import emp.emp.util.api_response.error_code.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TreatmentErrorCode implements ErrorCode {

  // 공통 오류
  TREATMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "TRT001", "진료일정을 찾을 수 없습니다."),
  ACCESS_DENIED(HttpStatus.FORBIDDEN, "TRT002", "이 진료일정에 접근할 권한이 없습니다."),
  INVALID_TREATMENT_DATA(HttpStatus.BAD_REQUEST, "TRT003", "진료일정 데이터가 유효하지 않습니다."),

  // 캘린더 이벤트 관련 오류
  CALENDAR_EVENT_NOT_FOUND(HttpStatus.NOT_FOUND, "TRT004", "연결할 캘린더 이벤트를 찾을 수 없습니다."),
  EVENT_ACCESS_DENIED(HttpStatus.FORBIDDEN, "TRT005", "이 캘린더 이벤트에 접근할 권한이 없습니다."),

  // 필드 유효성 검사 오류
  INVALID_LOCATION(HttpStatus.BAD_REQUEST, "TRT006", "진료 장소는 필수 입력 항목입니다."),
  INVALID_TIME_FORMAT(HttpStatus.BAD_REQUEST, "TRT007", "진료 시간 형식이 올바르지 않습니다."),
  PAST_TIME_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "TRT008", "과거 시간으로 진료일정을 등록할 수 없습니다."),

  // 데이터베이스 오류
  DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "TRT009", "데이터베이스 처리 중 오류가 발생했습니다."),

  // 동시성 오류
  CONCURRENT_MODIFICATION(HttpStatus.CONFLICT, "TRT010", "다른 사용자가 동시에 이 일정을 수정했습니다. 새로고침 후 다시 시도하세요.");

  private final HttpStatus httpStatus;
  private final String code;
  private final String message;
}
