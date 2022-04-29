package com.example.voucher;


import java.util.Arrays;

public enum CommandType {
	CREATE,
	LIST,
	EXIT,
	EMPTY;
	public static CommandType of(String commandTypeStr) {
		return Arrays.stream(values())
				.filter(voucherType -> voucherType.name().equals(commandTypeStr))
				.findAny()
				.orElse(EMPTY);
	}
}