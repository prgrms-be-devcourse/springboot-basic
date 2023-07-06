package com.prgms.springbootbasic.util;

import java.util.Arrays;
import java.util.List;

public enum VoucherMenu {

	CREATE("create"),
	LIST("list");

	private static final List<VoucherMenu> VOUCHER_MENU_VALUES = Arrays.stream(VoucherMenu.values()).toList();
	private final String command;

	VoucherMenu(String command) {
		this.command = command;
	}
	
	public static VoucherMenu of(String command) {
		return VOUCHER_MENU_VALUES.stream()
					.filter(m -> m.command.equals(command))
					.findFirst()
					.orElseThrow(() -> new IllegalArgumentException(ExceptionMessage.NO_SUCH_MENU.getMessage()));
	}
	
}
