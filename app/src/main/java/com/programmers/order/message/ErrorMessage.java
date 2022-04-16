package com.programmers.order.message;

public enum ErrorMessage {

	CLIENT_ERROR("4xx", "잘못된 입력 입니다."),
	INTERNAL_PROGRAM_ERROR("5xx", "프로그램 내부에 문제가 발생했습니다.");

	private String status;
	private String message;

	ErrorMessage(String status, String message) {
		this.status = status;
		this.message = message;
	}

	public String send() {
		return "'" + this.message + "'\n";
	}

	@Override
	public String toString() {
		return "ErrorMessage{" +
				"status='" + status + '\'' +
				", message='" + message + '\'' +
				'}';
	}
}
