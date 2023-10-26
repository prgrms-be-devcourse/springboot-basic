package com.programmers.springbasic.command.customer;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.programmers.springbasic.command.Command;
import com.programmers.springbasic.console.ConsoleInputHandler;
import com.programmers.springbasic.console.ConsoleOutputHandler;
import com.programmers.springbasic.controller.CustomerController;

@Component
public class DeleteCustomerCommand implements Command {

	private final CustomerController customerController;
	private final ConsoleInputHandler consoleInputHandler;
	private final ConsoleOutputHandler consoleOutputHandler;

	public DeleteCustomerCommand(CustomerController customerController, ConsoleInputHandler consoleInputHandler,
		ConsoleOutputHandler consoleOutputHandler) {
		this.customerController = customerController;
		this.consoleInputHandler = consoleInputHandler;
		this.consoleOutputHandler = consoleOutputHandler;
	}

	@Override
	public void execute() {
		consoleOutputHandler.printIdPrompt();
		UUID uuidInput = consoleInputHandler.readUUID();
		customerController.deleteCustomer(uuidInput);
	}
}
