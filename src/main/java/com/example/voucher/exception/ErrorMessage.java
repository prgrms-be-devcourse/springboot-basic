package com.example.voucher.exception;

public enum ErrorMessage {
	INVALID_INPUT("잘못된 입력입니다."),
	SERVER_ERROR("처리중 문제가 발생했습니다.");
	String message;

	ErrorMessage(String message) {
		this.message = message;
	}
}
