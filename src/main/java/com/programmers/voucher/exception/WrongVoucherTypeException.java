package com.programmers.voucher.exception;

public class WrongVoucherTypeException extends RuntimeException {

	public WrongVoucherTypeException() {
		super("바우처 이름을 잘못 입력하셨습니다.");
	}
}
