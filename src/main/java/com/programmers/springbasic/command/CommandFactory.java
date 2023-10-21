package com.programmers.springbasic.command;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.programmers.springbasic.console.ConsoleInputHandler;
import com.programmers.springbasic.console.ConsoleOutputHandler;
import com.programmers.springbasic.controller.CustomerController;
import com.programmers.springbasic.controller.VoucherController;
import com.programmers.springbasic.enums.MenuType;

@Component
public class CommandFactory {

	private final Map<MenuType, Command> commandMap = new ConcurrentHashMap<>();

	public CommandFactory(VoucherController voucherController, CustomerController customerController,
		ConsoleInputHandler consoleInputHandler, ConsoleOutputHandler consoleOutputHandler) {
		commandMap.put(MenuType.EXIT, new ExitCommand());
		commandMap.put(MenuType.LIST_VOUCHERS, new GetVouchersCommand(voucherController, consoleOutputHandler));
		commandMap.put(MenuType.CREATE_VOUCHER, new CreateVoucherCommand(voucherController, consoleInputHandler,
			consoleOutputHandler));
		commandMap.put(MenuType.LIST_BLACKLIST_CUSTOMERS,
			new GetBlacklistCustomersCommand(customerController, consoleOutputHandler));
	}

	public Command getCommand(MenuType menu) {
		return commandMap.get(menu);
	}
}
