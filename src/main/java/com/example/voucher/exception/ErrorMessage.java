package com.example.voucher.exception;

public enum ErrorMessage {
	INVALID_INPUT("잘못된 입력입니다."),
	SERVER_ERROR("처리중 문제가 발생했습니다."), UNSUPPORTED_MESSAGE_TYPE("지원하지 않는 메시지 타입입니다."),
	FILE_WRITE_ERROR("파일 저장에 실패했습니다."),
	FILE_READ_ERROR("파일 읽기에 실패했습니다."),
	FILE_CONTENT_ERROR("파일 내용에 문제가 있습니다.");

	String message;

	ErrorMessage(String message) {
		this.message = message;
	}
}