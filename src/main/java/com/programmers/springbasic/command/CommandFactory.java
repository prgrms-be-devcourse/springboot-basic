package com.programmers.springbasic.command;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.programmers.springbasic.command.customer.CreateCustomerCommand;
import com.programmers.springbasic.command.customer.DeleteCustomerCommand;
import com.programmers.springbasic.command.customer.GetAllCustomersCommand;
import com.programmers.springbasic.command.customer.GetBlacklistCustomersCommand;
import com.programmers.springbasic.command.customer.GetCustomerDetailCommand;
import com.programmers.springbasic.command.customer.UpdateCustomerCommand;
import com.programmers.springbasic.command.voucher.CreateVoucherCommand;
import com.programmers.springbasic.command.voucher.DeleteVoucherCommand;
import com.programmers.springbasic.command.voucher.GetAllVouchersCommand;
import com.programmers.springbasic.command.voucher.GetVoucherDetailCommand;
import com.programmers.springbasic.command.voucher.UpdateVoucherCommand;
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

		//voucher
		commandMap.put(CommandType.CREATE_VOUCHER,
			new CreateVoucherCommand(voucherController, consoleInputHandler, consoleOutputHandler));
		commandMap.put(CommandType.GET_ALL_VOUCHERS,
			new GetAllVouchersCommand(voucherController, consoleOutputHandler));
		commandMap.put(CommandType.GET_VOUCHER_DETAIL,
			new GetVoucherDetailCommand(voucherController, consoleInputHandler, consoleOutputHandler));
		commandMap.put(CommandType.UPDATE_VOUCHER,
			new UpdateVoucherCommand(voucherController, consoleInputHandler, consoleOutputHandler));
		commandMap.put(CommandType.DELETE_VOUCHER,
			new DeleteVoucherCommand(voucherController, consoleInputHandler, consoleOutputHandler));

		//customer
		commandMap.put(CommandType.CREATE_CUSTOMER,
			new CreateCustomerCommand(customerController, consoleInputHandler, consoleOutputHandler));
		commandMap.put(CommandType.GET_ALL_CUSTOMERS,
			new GetAllCustomersCommand(customerController, consoleOutputHandler));
		commandMap.put(CommandType.GET_BLACKLIST_CUSTOMERS,
			new GetBlacklistCustomersCommand(customerController, consoleOutputHandler));
		commandMap.put(CommandType.GET_CUSTOMER_DETAIL,
			new GetCustomerDetailCommand(customerController, consoleInputHandler, consoleOutputHandler));
		commandMap.put(CommandType.UPDATE_CUSTOMER,
			new UpdateCustomerCommand(customerController, consoleInputHandler, consoleOutputHandler));
		commandMap.put(CommandType.DELETE_CUSTOMER,
			new DeleteCustomerCommand(customerController, consoleInputHandler, consoleOutputHandler));
	}

	public Command getCommand(CommandType commandType) {
		return commandMap.get(commandType);
	}
}
