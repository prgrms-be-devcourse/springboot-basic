package com.programmers.order.type;

import java.util.Arrays;

public class DomainMenu {

	private DomainMenu() {
	}

	public enum MenuType {
		CREATE, LIST, EXIT, LIST_UP_WITH_CUSTOMER,DELETE, NONE;

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

	public enum CustomerMenuType {
		CREATE, REGISTER, LIST_UP_WITH_VOUCHER, UNMAPPING, EXIT, NONE;

		public static CustomerMenuType of(String input) {
			return Arrays.stream(CustomerMenuType.values())
					.filter((menu) -> menu.name().equalsIgnoreCase(input))
					.findAny()
					.orElseGet(() -> NONE);
		}

		public boolean isRunnable() {
			return this != EXIT;
		}

	}
}
