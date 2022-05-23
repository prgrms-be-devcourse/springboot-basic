package com.programmers.order.exception;

public enum ErrorMessage {

	CLIENT_ERROR("잘못 입력하였습니다. 다시 입력해 주세요.."),
	SERVER_ERROR("내부적으로 오류가 발생했습니다. 관리자에게 문의해주세요..");

	private final String message;

	ErrorMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
