package com.prgrms.vouchermanagement.commons.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler({IllegalArgumentException.class})
	public ApiResult<ApiError> handleIllegalArgumentException(IllegalArgumentException e) {
		logger.info("{}", e.getMessage(), e);

		return ApiResult.error(HttpStatus.BAD_REQUEST, e);
	}

	@ExceptionHandler({RuntimeException.class})
	public ApiResult<ApiError> handleRuntimeException(RuntimeException e) {
		logger.error("알 수 없는 런타임 예외 {}", e.getMessage(), e);

		return ApiResult.error(HttpStatus.INTERNAL_SERVER_ERROR, e);
	}

	@ExceptionHandler({Exception.class})
	public ApiResult<ApiError> handleRuntimeException(Exception e) {
		logger.error("알 수 없는 예외 {}", e.getMessage(), e);

		return ApiResult.error(HttpStatus.INTERNAL_SERVER_ERROR, e);
	}

}
