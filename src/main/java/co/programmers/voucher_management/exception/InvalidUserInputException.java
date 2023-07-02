package co.programmers.voucher_management.exception;

public class InvalidUserInputException extends RuntimeException {
	public InvalidUserInputException() {
		super();
	}

	public InvalidUserInputException(String message) {
		super(message);
	}
}
