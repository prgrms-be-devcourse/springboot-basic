package co.programmers.voucher_management.exception;

import java.time.LocalDateTime;

public record ErrorResponse(
		LocalDateTime timestamp,
		int statusCode,
		String error,
		String message
) {
	public ErrorResponse(ErrorCode errorCode) {
		this(
				LocalDateTime.now(),
				errorCode.getHttpStatus().value(),
				errorCode.getHttpStatus().name(),
				errorCode.getMessage()
		);
	}
}
