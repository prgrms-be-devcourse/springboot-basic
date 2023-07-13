package co.programmers.voucher_management.exception;

public class InvalidDataException extends RuntimeException {
	public InvalidDataException() {
	}

	public InvalidDataException(String message) {
		super(message);
	}
}
