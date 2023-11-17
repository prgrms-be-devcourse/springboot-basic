package study.dev.spring.common.exception.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import study.dev.spring.common.dto.ErrorResponse;
import study.dev.spring.common.exception.CommonException;

@RestControllerAdvice
public class ApiExceptionHandler {

	private final Logger logger = LoggerFactory.getLogger(ApiExceptionHandler.class);

	@ExceptionHandler(CommonException.class)
	public ResponseEntity<ErrorResponse> handleCustomException(CommonException e) {
		logger.error("custom exception : ", e);

		ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
		return ResponseEntity.badRequest().body(errorResponse);
	}

	@ExceptionHandler(CommonException.class)
	public ResponseEntity<ErrorResponse> handleServerException(Exception e) {
		logger.error("server exception : ", e);

		ErrorResponse errorResponse = new ErrorResponse("서버 내부 오류입니다. 다시 시도해주세요");
		return ResponseEntity.internalServerError().body(errorResponse);
	}
}
