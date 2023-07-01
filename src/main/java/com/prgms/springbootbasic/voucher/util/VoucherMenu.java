package com.prgms.springbootbasic.voucher.util;

import com.prgms.springbootbasic.voucher.controller.VoucherController;
import com.prgms.springbootbasic.global.util.ExceptionMessage;
import com.prgms.springbootbasic.global.util.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public enum VoucherMenu {

	CREATE("create", Application::voucherCreateController),
	LIST("list", Application::voucherListController);

	private static final List<VoucherMenu> VOUCHER_MENU_VALUES = Arrays.stream(VoucherMenu.values()).toList();
	private static final Logger logger = LoggerFactory.getLogger(VoucherMenu.class);
	private final String command;
	private final Supplier<VoucherController> controllerFactory;

	
	VoucherMenu(String command, Supplier<VoucherController> controllerFactory) {
		this.command = command;
		this.controllerFactory = controllerFactory;
	}

	public VoucherController getCommandLineController() {
		return controllerFactory.get();
	}
	
	public static VoucherMenu of(String command) {
		return VOUCHER_MENU_VALUES.stream()
					.filter(m -> m.command.equals(command))
					.findFirst()
					.orElseThrow(() -> {
						logger.warn("해당하는 메뉴가 없습니다. 입력한 값 : {}", command);
						return new IllegalArgumentException(ExceptionMessage.NO_SUCH_MENU.getMessage());
					});
	}
	
}
