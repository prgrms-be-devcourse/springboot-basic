package com.programmers.springbasic.constants;

public enum ErrorCode {
	INVALID_COMMAND_TYPE("메뉴 선택이 잘못되었습니다"),
	INVALID_VOUCHER_TYPE("잘못된 바우처 타입입니다"),
	INVALID_NUMBER("숫자 입력이 잘못되었습니다"),
	INVALID_UUID("uuid 입력이 잘못되었습니다"),
	INVALID_EMAIL("이메일 형식이 잘못되었습니다"),
	INVALID_NAME("이름 형식이 잘못되었습니다"),
	AMOUNT_SHOULD_BE_POSITIVE("금액은 0보다 커야 합니다"),
	PERCENT_OUT_OF_RANGE("퍼센트는 0과 100 사이여야 합니다"),
	EMPTY_STRING("문자열은 비어있으면 안됩니다"),
	FILE_CANNOT_READ("파일 읽기에 실패했습니다"),
	FILE_CANNOT_WRITE("파일 쓰기에 실패했습니다"),
	FILE_CANNOT_CREATE("파일 생성에 실패했습니다"),
	FILE_NOT_FOUND("파일이 존재하지 않습니다"),
	CUSTOMER_NOT_FOUND("존재하지 않는 고객입니다"),
	VOUCHER_NOT_FOUND("존재하지 않는 바우처입니다");

	private final String message;

	ErrorCode(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
