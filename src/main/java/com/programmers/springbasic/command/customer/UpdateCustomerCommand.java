package com.programmers.springbasic.command.customer;

import com.programmers.springbasic.command.Command;
import com.programmers.springbasic.console.ConsoleInputHandler;
import com.programmers.springbasic.console.ConsoleOutputHandler;
import com.programmers.springbasic.controller.CustomerController;

public class UpdateCustomerCommand implements Command {

	private final CustomerController customerController;
	private final ConsoleInputHandler consoleInputHandler;

	private final ConsoleOutputHandler consoleOutputHandler;

	public UpdateCustomerCommand(CustomerController customerController, ConsoleInputHandler consoleInputHandler,
		ConsoleOutputHandler consoleOutputHandler) {
		this.customerController = customerController;
		this.consoleInputHandler = consoleInputHandler;
		this.consoleOutputHandler = consoleOutputHandler;
	}

	@Override
	public void execute() {
		consoleOutputHandler.printIdPrompt();
		String uuidInput = consoleInputHandler.readString();
		consoleOutputHandler.printObject("name : ");
		String nameInput = consoleInputHandler.readString();
		customerController.updateCustomer(uuidInput, nameInput);
	}
}
