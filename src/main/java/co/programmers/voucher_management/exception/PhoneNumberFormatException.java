package co.programmers.voucher_management.exception;

public class PhoneNumberFormatException extends RuntimeException {
	public PhoneNumberFormatException() {
	}

	public PhoneNumberFormatException(String message) {
		super(message);
	}
}
