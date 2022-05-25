package com.programmers.order.type;

import java.util.Arrays;

public enum ProgramType {
	NONE("-88", "demoController"),
	CUSTOMER("1", "customerController"),
	VOUCHER("2", "voucherController"),
	EXIT("3", "Exit");

	private final String key;
	private final String beanName;

	ProgramType(String key, String beanName) {
		this.key = key;
		this.beanName = beanName;
	}

	public static ProgramType of(String input) {
		return Arrays.stream(ProgramType.values())
				.filter(program -> program.key.equals(input))
				.findAny().orElseGet(() -> NONE);
	}

	public String getBeanName() {
		return beanName;
	}

	public boolean isNotExit() {
		return this != ProgramType.EXIT;
	}
}
