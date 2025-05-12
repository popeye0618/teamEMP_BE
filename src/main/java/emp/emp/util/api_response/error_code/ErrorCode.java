package emp.emp.util.api_response.error_code;

import org.springframework.http.HttpStatus;

public interface ErrorCode {

	String getCode();

	HttpStatus getHttpStatus();

	String getMessage();

}
