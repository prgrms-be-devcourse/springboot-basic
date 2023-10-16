package com.programmers.springbasic.entity;

import static com.programmers.springbasic.ErrorCode.*;

import java.util.Arrays;

public enum MenuType {
	CREATE_VOUCHER("create"),
	LIST_VOUCHERS("list"),
	LIST_BLACKLIST_CUSTOMERS("blacklist"),
	EXIT("exit");

	private final String message;

	MenuType(String message) {
		this.message = message;
	}

	public static MenuType from(String readString) {
		return Arrays.stream(MenuType.values())
			.filter(type -> type.message.equalsIgnoreCase(readString))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException(INVALID_MENU.getMessage()));
	}
}
