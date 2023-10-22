package com.programmers.springbasic.enums;

import static com.programmers.springbasic.enums.ErrorCode.*;

import java.util.Arrays;

public enum CommandType {
	CREATE_VOUCHER("create"),
	LIST_VOUCHERS("list"),
	LIST_BLACKLIST_CUSTOMERS("blacklist"),
	EXIT("exit");

	private final String message;

	CommandType(String message) {
		this.message = message;
	}

	public static CommandType from(String readString) {
		return Arrays.stream(CommandType.values())
			.filter(type -> type.message.equalsIgnoreCase(readString))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException(INVALID_COMMAND_TYPE.getMessage()));
	}
}
