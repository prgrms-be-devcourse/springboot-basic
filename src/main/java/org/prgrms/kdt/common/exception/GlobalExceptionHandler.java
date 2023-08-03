package org.prgrms.kdt.common.exception;

import org.prgrms.kdt.common.response.ErrorResponse;
import org.prgrms.kdt.model.repository.file.FileVoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(FileVoucherRepository.class);

	@ExceptionHandler(VoucherRuntimeException.class)
	protected ErrorResponse handleRuntimeException(VoucherRuntimeException ex) {
		logger.error("Common Runtime Exception", ex);
		return new ErrorResponse(ex.getErrorCode());
	}
}
