package co.programmers.voucher_management.exception;

public class NoSuchVoucherException extends RuntimeException {
	public NoSuchVoucherException() {
	}

	public NoSuchVoucherException(String message) {
		super(message);
	}
}
