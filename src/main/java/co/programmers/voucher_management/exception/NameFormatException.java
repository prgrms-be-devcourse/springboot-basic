package co.programmers.voucher_management.exception;

public class NameFormatException extends RuntimeException {
	public NameFormatException() {
		super("The name must be between 2 and 30 characters in Korean "
				+ "or between 2 and 50 characters in English.");
	}

	public NameFormatException(String message) {
		super(message);
	}
}
