package com.programmers.voucher.exception;

public enum ExceptionMessage {

	OUT_OF_PERCENT_RANGE("할인 비율은 0보다 크고 100이하여야 합니다."),
	VOUCHER_NOT_FOUND("바우처를 찾을 수 없습니다."),
	WRONG_COMMAND("명령어를 다시 입력해주세요."),
	WRONG_DISCOUNT_TYPE("할인값을 숫자로 입력해주세요"),
	WRONG_VOUCHER_TYPE("바우처 이름을 잘못 입력하셨습니다."),
	IO("IoException 발생");

	private String message;

	ExceptionMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
