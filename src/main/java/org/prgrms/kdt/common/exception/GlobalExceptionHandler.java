package org.prgrms.kdt.common.exception;

import org.prgrms.kdt.common.response.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(VoucherRuntimeException.class)
	protected ErrorResponse handleRuntimeException(VoucherRuntimeException ex) {
		logger.error("Voucher Runtime Exception", ex);
		return new ErrorResponse(ex.getErrorCode());
	}

	@ExceptionHandler(CustomerRuntimeException.class)
	protected ErrorResponse handleRuntimeException(CustomerRuntimeException ex) {
		logger.error("Customer Runtime Exception", ex);
		return new ErrorResponse(ex.getErrorCode());
	}
}
