package com.programmers.springbasic;

public enum ErrorCode {
	INVALID_MENU("메뉴 선택이 잘못되었습니다"),
	INVALID_VOUCHER_TYPE("잘못된 바우처 타입입니다"),
	INVALID_NUMBER("숫자 입력이 잘못되었습니다"),
	AMOUNT_SHOULD_BE_POSITIVE("금액은 0보다 커야 합니다"),
	PERCENT_OUT_OF_RANGE("퍼센트는 0과 100 사이여야 합니다"),
	EMPTY_STRING("문자열은 비어있으면 안됩니다");

	private final String message;

	ErrorCode(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
