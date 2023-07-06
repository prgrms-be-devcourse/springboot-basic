package co.programmers.voucher_management.exception;

public class MenuTypeMismatchException extends RuntimeException {
	public MenuTypeMismatchException() {
	}

	public MenuTypeMismatchException(String message) {
		super(message);
	}
}
