package com.programmers.springbasic.command;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.programmers.springbasic.command.customer.GetBlacklistCustomersCommand;
import com.programmers.springbasic.command.voucher.CreateVoucherCommand;
import com.programmers.springbasic.command.voucher.GetVouchersCommand;
import com.programmers.springbasic.console.ConsoleInputHandler;
import com.programmers.springbasic.console.ConsoleOutputHandler;
import com.programmers.springbasic.controller.CustomerController;
import com.programmers.springbasic.controller.VoucherController;
import com.programmers.springbasic.enums.CommandType;

@Component
public class CommandFactory {

	private final Map<CommandType, Command> commandMap = new ConcurrentHashMap<>();

	public CommandFactory(VoucherController voucherController, CustomerController customerController,
		ConsoleInputHandler consoleInputHandler, ConsoleOutputHandler consoleOutputHandler) {
		commandMap.put(CommandType.EXIT, new ExitCommand());
		commandMap.put(CommandType.LIST_VOUCHERS, new GetVouchersCommand(voucherController, consoleOutputHandler));
		commandMap.put(CommandType.CREATE_VOUCHER, new CreateVoucherCommand(voucherController, consoleInputHandler,
			consoleOutputHandler));
		commandMap.put(CommandType.LIST_BLACKLIST_CUSTOMERS,
			new GetBlacklistCustomersCommand(customerController, consoleOutputHandler));
	}

	public Command getCommand(CommandType commandType) {
		return commandMap.get(commandType);
	}
}
