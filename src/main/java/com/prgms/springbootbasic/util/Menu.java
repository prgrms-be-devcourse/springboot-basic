package com.prgms.springbootbasic.util;

import com.prgms.springbootbasic.controller.VoucherController;
import com.prgms.springbootbasic.exception.NoSuchMenuException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public enum Menu {
	
	EXIT("exit", VoucherApplication::exitController),
	CREATE("create", VoucherApplication::voucherCreateController),
	LIST("list", VoucherApplication::voucherListController);

	private static final List<Menu> MENU_VALUES = Arrays.stream(Menu.values()).toList();
	private static final Logger logger = LoggerFactory.getLogger(Menu.class);
	private final String command;
	private final Supplier<VoucherController> controllerFactory;

	
	Menu(String command, Supplier<VoucherController> controllerFactory) {
		this.command = command;
		this.controllerFactory = controllerFactory;
	}

	public VoucherController getCommandLineController() {
		return controllerFactory.get();
	}
	
	public static Menu of(String command) {
		return MENU_VALUES.stream()
					.filter(m -> m.command.equals(command))
					.findFirst()
					.orElseThrow(() -> {
						logger.warn("해당하는 메뉴가 없습니다. 입력한 값 : {}", command);
						return new NoSuchMenuException(ExceptionMessage.NO_SUCH_MENU);
					});
	}
	
}
