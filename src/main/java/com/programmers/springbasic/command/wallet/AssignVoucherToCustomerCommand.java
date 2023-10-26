package com.programmers.springbasic.command.wallet;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.programmers.springbasic.command.Command;
import com.programmers.springbasic.console.ConsoleInputHandler;
import com.programmers.springbasic.console.ConsoleOutputHandler;
import com.programmers.springbasic.controller.CustomerController;

@Component
public class AssignVoucherToCustomerCommand implements Command {

	private final CustomerController customerController;
	private final ConsoleInputHandler consoleInputHandler;
	private final ConsoleOutputHandler consoleOutputHandler;

	public AssignVoucherToCustomerCommand(CustomerController customerController,
		ConsoleInputHandler consoleInputHandler,
		ConsoleOutputHandler consoleOutputHandler) {
		this.customerController = customerController;
		this.consoleInputHandler = consoleInputHandler;
		this.consoleOutputHandler = consoleOutputHandler;
	}

	@Override
	public void execute() {
		consoleOutputHandler.printObject("고객 아이디 : ");
		UUID customerId = consoleInputHandler.readUUID();
		consoleOutputHandler.printObject("바우처 아이디 : ");
		UUID voucherId = consoleInputHandler.readUUID();
		customerController.assignVoucherToCustomer(customerId, voucherId);
	}
}
