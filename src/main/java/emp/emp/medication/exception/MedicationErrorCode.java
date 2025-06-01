package emp.emp.medication.exception;

import org.springframework.http.HttpStatus;
import emp.emp.util.api_response.error_code.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MedicationErrorCode implements ErrorCode {

  // 일반적인 오류들
  MEDICATION_NOT_FOUND(HttpStatus.NOT_FOUND, "MED001", "복약관리 정보를 찾을 수 없습니다."),
  ACCESS_DENIED(HttpStatus.FORBIDDEN, "MED002", "이 복약관리 정보에 접근할 권한이 없습니다."),
  INVALID_MEDICATION_DATA(HttpStatus.BAD_REQUEST, "MED003", "복약관리 데이터가 유효하지 않습니다."),

  // 병명 관련 오류들
  DISEASE_NAME_REQUIRED(HttpStatus.BAD_REQUEST, "MED004", "병명은 필수 입력 사항입니다."),
  DISEASE_NAME_TOO_LONG(HttpStatus.BAD_REQUEST, "MED005", "병명은 최대 10자까지 입력 가능합니다."),

  // 날짜 관련 오류들
  INVALID_DATE_RANGE(HttpStatus.BAD_REQUEST, "MED006", "복약 종료일은 시작일보다 늦어야 합니다."),
  START_DATE_REQUIRED(HttpStatus.BAD_REQUEST, "MED007", "복약 시작일은 필수 입력 사항입니다."),
  END_DATE_REQUIRED(HttpStatus.BAD_REQUEST, "MED008", "복약 종료일은 필수 입력 사항입니다."),

  // 약물 관련 오류들
  DRUG_LIST_EMPTY(HttpStatus.BAD_REQUEST, "MED009", "최소 1개 이상의 약물 정보를 입력해야 합니다."),
  DRUG_NAME_REQUIRED(HttpStatus.BAD_REQUEST, "MED010", "약물명은 필수 입력 사항입니다."),
  DOSAGE_REQUIRED(HttpStatus.BAD_REQUEST, "MED011", "복용량은 필수 입력 사항입니다."),

  // 복약시기 관련 오류들
  TIMING_LIST_EMPTY(HttpStatus.BAD_REQUEST, "MED012", "최소 1개 이상의 복약시기를 선택해야 합니다."),
  TIMING_TYPE_REQUIRED(HttpStatus.BAD_REQUEST, "MED013", "복약시기 타입은 필수 선택 사항입니다."),

  // 캘린더 이벤트 관련 오류들
  CALENDAR_EVENT_NOT_FOUND(HttpStatus.NOT_FOUND, "MED014", "연결할 캘린더 이벤트를 찾을 수 없습니다."),
  EVENT_TYPE_INVALID(HttpStatus.BAD_REQUEST, "MED015", "복약관리는 복약관리 일정에만 등록할 수 있습니다."),
  MEDICATION_ALREADY_EXISTS(HttpStatus.CONFLICT, "MED016", "해당 일정에 이미 복약관리가 등록되어 있습니다."),

  // 가족 공유 관련 오류들
  FAMILY_NOT_FOUND(HttpStatus.NOT_FOUND, "MED017", "가족 정보를 찾을 수 없습니다."),

  // 데이터베이스 오류
  DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "MED018", "데이터베이스 처리 중 오류가 발생했습니다.");

  private final HttpStatus httpStatus;
  private final String code;
  private final String message;
}