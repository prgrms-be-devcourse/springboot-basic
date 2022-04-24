package com.prgrms.vouchermanagement.commons;

public enum ErrorMessage {
	CREATION_FAIL("생성에 실패하였습니다"),
	NO_MAPPING("매핑 되는 것이 존재하지 않습니다");

	private final String message;

	ErrorMessage(String message) {
		this.message = message;
	}

	public String getInfoMessage() {
		return message;
	}
}
