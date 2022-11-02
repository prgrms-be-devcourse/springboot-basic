package com.programmers.voucher.exception;

public class WrongCommandException extends RuntimeException {

	public WrongCommandException() {
		super("명령어를 다시 입력해주세요.");
	}
}
