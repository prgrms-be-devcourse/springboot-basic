package co.programmers.voucher_management.exception;

import lombok.Getter;

@Getter

public class InvalidDataException extends RuntimeException {
	private final ErrorCode errorCode;

	public InvalidDataException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}
}
