package com.prgrms.vouchermanagement.commons;

public enum ErrorMessage {
	CREATION_FAIL("생성에 실패하였습니다"),
	NO_MAPPING("매핑 되는 것이 존재하지 않습니다"),
	NOT_EXIST("존재 하지 않는 정보입니다"),
	UPDATE_FAIL("업데이트 실패!!"),
	ALREADY_EXIST("이미 존재합니다"),
	DELETION_FAIL("삭제 실패!!");

	private final String message;

	ErrorMessage(String message) {
		this.message = message;
	}

	public String getInfoMessage() {
		return message;
	}
}
