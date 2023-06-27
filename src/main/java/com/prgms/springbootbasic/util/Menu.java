package com.prgms.springbootbasic.util;

import com.prgms.springbootbasic.controller.VoucherController;
import com.prgms.springbootbasic.controller.ExitController;
import com.prgms.springbootbasic.controller.VoucherCreateController;
import com.prgms.springbootbasic.controller.VoucherListController;
import com.prgms.springbootbasic.exception.NoSuchMenuException;
import com.prgms.springbootbasic.ui.Console;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public enum Menu {
	
	EXIT("exit", ExitController::new),
	CREATE("create", VoucherCreateController::new),
	LIST("list", VoucherListController::new);
	
	private static final String ERROR_NO_SUCH_MENU = "[Error] Can't Find Such Menu";
	private static final List<Menu> MENU_VALUES = Arrays.stream(Menu.values()).toList();
	private final String command;
	private final Function<Console, VoucherController> controllerFactory;
	
	Menu(String command, Function<Console, VoucherController> controllerFactory) {
		this.command = command;
		this.controllerFactory = controllerFactory;
	}

	public VoucherController getCommandLineController(Console console) {
		return controllerFactory.apply(console);
	}
	
	public static Menu of(String command) {
		return MENU_VALUES.stream()
					.filter(m -> m.command.equals(command))
					.findFirst()
					.orElseThrow(() -> new NoSuchMenuException(ERROR_NO_SUCH_MENU));
	}
	
}
