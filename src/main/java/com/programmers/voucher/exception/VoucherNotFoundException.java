package com.programmers.voucher.exception;

public class VoucherNotFoundException extends RuntimeException {

	public VoucherNotFoundException() {
		super(ExceptionMessage.VOUCHER_NOT_FOUND.getMessage());
	}
}
