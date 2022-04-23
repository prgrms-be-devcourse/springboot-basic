package com.prgrms.vouchermanagement.commons.exception;

public class NoMappingOneException extends RuntimeException {

	private final String input;

	public NoMappingOneException(String input) {
		super("매핑 되는 것이 존재하지 않습니다");

		this.input = input;
	}

	public String getInput() {
		return input;
	}
}
