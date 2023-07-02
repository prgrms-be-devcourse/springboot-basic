package co.programmers.voucher_management.exception;

public class NoSuchTypeException extends RuntimeException {
	public NoSuchTypeException() {
		super();
	}

	public NoSuchTypeException(String message) {
		super(message);
	}
}
