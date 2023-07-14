package co.programmers.voucher_management.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler(EmptyAssignerException.class)
	protected ResponseEntity<ErrorResponse> handleCustomException(EmptyAssignerException emptyAssignerException) {
		ErrorCode errorCode = emptyAssignerException.getErrorCode();
		return handleExceptionInternal(errorCode);
	}

	@ExceptionHandler(InvalidDataException.class)
	protected ResponseEntity<ErrorResponse> handleCustomException(InvalidDataException invalidDataException) {
		ErrorCode errorCode = invalidDataException.getErrorCode();
		return handleExceptionInternal(errorCode);
	}

	@ExceptionHandler(NoSuchDataException.class)
	protected ResponseEntity<ErrorResponse> handleCustomException(NoSuchDataException noSuchDataException) {
		ErrorCode errorCode = noSuchDataException.getErrorCode();
		return handleExceptionInternal(errorCode);
	}

	@ExceptionHandler(VoucherReassignmentException.class)
	protected ResponseEntity<ErrorResponse> handleCustomException(
			VoucherReassignmentException voucherReassignmentException) {
		ErrorCode errorCode = voucherReassignmentException.getErrorCode();
		return handleExceptionInternal(errorCode);
	}

	private ResponseEntity<ErrorResponse> handleExceptionInternal(ErrorCode errorCode) {
		return ResponseEntity
				.status(errorCode.getHttpStatus().value())
				.body(new ErrorResponse(errorCode));
	}
}
