package co.programmers.voucher_management.exception;

public class InvalidVoucherAmountException extends RuntimeException {
	public InvalidVoucherAmountException() {
		super();
	}

	public InvalidVoucherAmountException(String message) {
		super(message);
	}
}
