package com.programmers.basic.command;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.programmers.basic.console.InputHandler;
import com.programmers.basic.console.OutputHandler;
import com.programmers.basic.controller.VoucherController;
import com.programmers.basic.entity.MenuType;

@Component
public class CommandFactory {
	private final Map<MenuType, Command> commandMap = new ConcurrentHashMap<>();

	public CommandFactory(VoucherController voucherController, InputHandler inputHandler, OutputHandler outputHandler) {
		commandMap.put(MenuType.LIST_VOUCHERS, new ListVouchersCommand(voucherController, outputHandler));
		commandMap.put(MenuType.CREATE_VOUCHER, new CreateVoucherCommand(voucherController, inputHandler,
			outputHandler));
	}

	public Command getCommand(MenuType menu) {
		return commandMap.get(menu);
	}
}
