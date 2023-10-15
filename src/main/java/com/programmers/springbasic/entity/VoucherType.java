package com.programmers.springbasic.entity;

import java.util.Arrays;

public enum VoucherType {
	FIXED_AMOUNT("1"), PERCENT_DISCOUNT("2");

	private final String message;

	VoucherType(String message) {
		this.message = message;
	}

	public static VoucherType from(String readString) {
		return Arrays.stream(VoucherType.values())
			.filter(type -> type.message.equalsIgnoreCase(readString))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("No enum constant " + VoucherType.class.getCanonicalName() + " for string: " + readString));
	}
}
