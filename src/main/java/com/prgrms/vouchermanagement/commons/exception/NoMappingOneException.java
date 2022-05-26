package com.prgrms.vouchermanagement.commons.exception;

import com.prgrms.vouchermanagement.commons.ErrorMessage;

public class NoMappingOneException extends RuntimeException {

	private final String input;

	public NoMappingOneException(String input) {
		super(ErrorMessage.NO_MAPPING.getInfoMessage());

		this.input = input;
	}

	public String getInput() {
		return input;
	}
}
