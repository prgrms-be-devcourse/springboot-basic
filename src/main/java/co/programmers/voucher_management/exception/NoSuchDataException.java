package co.programmers.voucher_management.exception;

public class NoSuchDataException extends RuntimeException {
	public NoSuchDataException() {
	}

	public NoSuchDataException(String message) {
		super(message);
	}
}
