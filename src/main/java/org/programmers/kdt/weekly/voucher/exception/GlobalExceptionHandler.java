package org.programmers.kdt.weekly.voucher.exception;

import org.programmers.kdt.weekly.voucher.controller.response.ErrorCode;
import org.programmers.kdt.weekly.voucher.controller.response.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(InvalidVoucherValueException.class)
	public ErrorResponse handleInvalidVoucherValueException(InvalidVoucherValueException e) {
		logger.debug("handleInvalidVoucherValueException -> {}", e.getMessage());

		return new ErrorResponse(e.getErrorCode(), e.getMessage());
	}

	@ExceptionHandler(NotFoundEntityByIdException.class)
	public ErrorResponse handleNotFoundEntityByIdException(NotFoundEntityByIdException e) {
		logger.debug("handleNotFoundEntityByIdException -> {}", e.getMessage());

		return new ErrorResponse(e.getErrorCode(), e.getMessage());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		var bindingResult = e.getBindingResult();
		StringBuilder bindingErrorMessage = getBindingErrorMessage(bindingResult);

		logger.debug("handleMethodArgumentNotValidException -> {}", bindingErrorMessage);

		return new ErrorResponse(ErrorCode.INVALID_REQUEST_VALUE, bindingErrorMessage.toString());
	}

	@ExceptionHandler(InvalidDateRequestException.class)
	public ErrorResponse handleInvalidDateRequestException(InvalidDateRequestException e) {
		logger.debug("handleInvalidDateRequestException -> {}", e.getMessage());

		return new ErrorResponse(e.getErrorCode(), e.getMessage());
	}

	@ExceptionHandler(FailedToExecuteSqlException.class)
	public ErrorResponse handleFailedToExecuteSqlException(FailedToExecuteSqlException e) {
		logger.warn("쿼리 실행 실패 -> {}", e.getMessage());

		return new ErrorResponse(e.getErrorCode(), e.getMessage());
	}

	private StringBuilder getBindingErrorMessage(BindingResult bindingResult) {
		var bindingErrorMessage = new StringBuilder();

		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			bindingErrorMessage
				.append("[ 에러 메세지 : ")
				.append(fieldError.getDefaultMessage())
				.append(", 입력된 값 : ")
				.append(fieldError.getRejectedValue())
				.append(" ]\n");
		}
		return bindingErrorMessage;
	}
}
