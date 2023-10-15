package com.programmers.springbasic.entity;

import java.util.Arrays;

public enum MenuType {
	CREATE_VOUCHER("create"),
	LIST_VOUCHERS("list"),
	EXIT("exit");

	private final String message;

	MenuType(String message) {
		this.message = message;
	}

	public static MenuType from(String readString) {
		return Arrays.stream(MenuType.values())
			.filter(type -> type.message.equalsIgnoreCase(readString))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("No enum constant " + MenuType.class.getCanonicalName() + " for string: " + readString));
	}
}
