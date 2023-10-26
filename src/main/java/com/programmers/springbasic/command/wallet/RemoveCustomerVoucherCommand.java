package com.programmers.springbasic.command.wallet;

import org.springframework.stereotype.Component;

import com.programmers.springbasic.command.Command;
import com.programmers.springbasic.console.ConsoleInputHandler;
import com.programmers.springbasic.console.ConsoleOutputHandler;
import com.programmers.springbasic.controller.CustomerController;

@Component
public class RemoveCustomerVoucherCommand implements Command {

	private final CustomerController customerController;
	private final ConsoleInputHandler consoleInputHandler;
	private final ConsoleOutputHandler consoleOutputHandler;

	public RemoveCustomerVoucherCommand(CustomerController customerController, ConsoleInputHandler consoleInputHandler,
		ConsoleOutputHandler consoleOutputHandler) {
		this.customerController = customerController;
		this.consoleInputHandler = consoleInputHandler;
		this.consoleOutputHandler = consoleOutputHandler;
	}

	@Override
	public void execute() {
		consoleOutputHandler.printObject("고객 아이디 : ");
		String customerId = consoleInputHandler.readString();
		consoleOutputHandler.printObject("바우처 아이디 : ");
		String voucherId = consoleInputHandler.readString();
		customerController.removeVoucherFromCustomer(customerId, voucherId);
	}
}
