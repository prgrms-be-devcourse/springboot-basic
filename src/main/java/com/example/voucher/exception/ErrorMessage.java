package com.example.voucher.exception;

public enum ErrorMessage {
	INVALID_INPUT("잘못된 입력입니다.");

	String message;

	ErrorMessage(String message) {
		this.message = message;
	}
}
