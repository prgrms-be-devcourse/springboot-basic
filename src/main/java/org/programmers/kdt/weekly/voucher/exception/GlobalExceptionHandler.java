package org.programmers.kdt.weekly.voucher.exception;

import org.programmers.kdt.weekly.voucher.controller.response.ErrorCode;
import org.programmers.kdt.weekly.voucher.controller.response.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(InvalidVoucherValueException.class)
	public ResponseEntity<ErrorResponse> handleInvalidVoucherValueException(InvalidVoucherValueException e) {
		logger.warn("handleInvalidVoucherValueException -> {}", e.getMessage());

		return ResponseEntity.status(e.getErrorCode().getStatusCode())
			.body(new ErrorResponse(e.getErrorCode(), e.getMessage()));
	}

	@ExceptionHandler(NotFoundEntityByIdException.class)
	public ResponseEntity<ErrorResponse> handleNotFoundEntityByIdException(NotFoundEntityByIdException e) {
		logger.warn("handleNotFoundEntityByIdException -> {}", e.getMessage());

		return ResponseEntity.status(e.getErrorCode().getStatusCode())
			.body(new ErrorResponse(e.getErrorCode(), e.getMessage()));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
		final MethodArgumentNotValidException e) {
		var bindingResult = e.getBindingResult();
		var stringBuilder = new StringBuilder();

		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			stringBuilder.append("에러 필드 -> ")
				.append(fieldError.getField())
				.append(", 에러 메세지 -> ")
				.append(fieldError.getDefaultMessage())
				.append(", 입력된 값 -> ")
				.append(fieldError.getRejectedValue());
		}
		logger.warn("handleMethodArgumentNotValidException -> {}", stringBuilder, e);

		return ResponseEntity.status(ErrorCode.INVALID_REQUEST_VALUE.getStatusCode())
			.body(new ErrorResponse(ErrorCode.INVALID_REQUEST_VALUE, stringBuilder.toString()));
	}
}
