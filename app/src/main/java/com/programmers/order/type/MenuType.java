package com.programmers.order.type;

import java.util.Arrays;

public enum MenuType {
	CREATE, LIST, EXIT, NONE;

	public static MenuType of(String input) {
		return Arrays.stream(MenuType.values())
				.filter((menu) -> menu.name().equalsIgnoreCase(input))
				.findAny()
				.orElseGet(() -> NONE);
	}

	public boolean isReEnter() {
		return this != EXIT;
	}
}

