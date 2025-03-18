package emp.emp.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import emp.emp.util.api_response.Response;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<Response<Void>> handleCustomException(BusinessException ex) {
		// ErrorCode를 기반으로 에러 응답 객체 생성
		Response<Void> response = Response.errorResponse(ex.getErrorCode());

		// Response 객체에 설정된 HTTP 상태 코드를 사용하여 ResponseEntity로 변환
		return response.toResponseEntity();
	}
}
