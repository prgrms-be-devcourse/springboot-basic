package co.programmers.voucher_management.exception;

public class EmptyAssignerException extends RuntimeException {
	public EmptyAssignerException() {
	}

	public EmptyAssignerException(String message) {
		super(message);
	}
}
