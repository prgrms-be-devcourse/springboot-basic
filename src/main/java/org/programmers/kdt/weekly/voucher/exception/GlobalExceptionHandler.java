package org.programmers.kdt.weekly.voucher.exception;

import org.programmers.kdt.weekly.voucher.controller.response.ErrorCode;
import org.programmers.kdt.weekly.voucher.controller.response.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public ErrorResponse handleUnexpectedException(Exception e) {
		logger.error("Unexpected Exception -> {}", e.getMessage(), e);

		return new ErrorResponse(ErrorCode.UNEXPECTED_EXCEPTION, e.getMessage());
	}

	@ExceptionHandler(RuntimeException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorResponse handleUnexpectedRuntimeException(RuntimeException e) {
		logger.error("Unexpected RuntimeException -> {}", e.getMessage(), e);

		return new ErrorResponse(ErrorCode.UNEXPECTED_EXCEPTION, e.getMessage());
	}

	@ExceptionHandler(InvalidVoucherValueException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleInvalidVoucherValueException(InvalidVoucherValueException e) {
		logger.warn("handleInvalidVoucherValueException -> {}", e.getMessage());

		return new ErrorResponse(e.getErrorCode(), e.getMessage());
	}

	@ExceptionHandler(NotFoundEntityByIdException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleNotFoundEntityByIdException(NotFoundEntityByIdException e) {
		logger.warn("handleNotFoundEntityByIdException -> {}", e.getMessage());

		return new ErrorResponse(e.getErrorCode(), e.getMessage());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		var errorFields = ErrorResponse.bindErrorFields(e.getBindingResult());
		logger.info("handleMethodArgumentNotValidException -> {}", errorFields, e);

		return new ErrorResponse(ErrorCode.INVALID_REQUEST_VALUE, "binding error", errorFields);
	}

	@ExceptionHandler(InvalidDateRequestException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleInvalidDateRequestException(InvalidDateRequestException e) {
		logger.warn("handleInvalidDateRequestException -> {}", e.getMessage());

		return new ErrorResponse(e.getErrorCode(), e.getMessage());
	}

	@ExceptionHandler(FailedToExecuteSqlException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorResponse handleFailedToExecuteSqlException(FailedToExecuteSqlException e) {
		logger.warn("쿼리 실행 실패 -> {}", e.getMessage());

		return new ErrorResponse(e.getErrorCode(), e.getMessage());
	}

	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleIllegalArgumentException(IllegalArgumentException e) {
		logger.warn("사용자 입력 에러 -> {}", e.getMessage());

		return new ErrorResponse(ErrorCode.INVALID_REQUEST_VALUE, e.getMessage());
	}
}
