package com.programmers.springbasic.command.customer;

import static com.programmers.springbasic.enums.MessageConstants.*;

import org.springframework.stereotype.Component;

import com.programmers.springbasic.command.Command;
import com.programmers.springbasic.console.ConsoleInputHandler;
import com.programmers.springbasic.console.ConsoleOutputHandler;
import com.programmers.springbasic.controller.CustomerController;

@Component
public class CreateCustomerCommand implements Command {

	private final CustomerController customerController;
	private final ConsoleInputHandler consoleInputHandler;
	private final ConsoleOutputHandler consoleOutputHandler;

	public CreateCustomerCommand(CustomerController customerController, ConsoleInputHandler consoleInputHandler,
		ConsoleOutputHandler consoleOutputHandler) {
		this.customerController = customerController;
		this.consoleInputHandler = consoleInputHandler;
		this.consoleOutputHandler = consoleOutputHandler;
	}

	@Override
	public void execute() {
		consoleOutputHandler.print(NAME_PROMPT);
		String nameInput = consoleInputHandler.readString();
		consoleOutputHandler.print(EMAIL_PROMPT);
		String emailInput = consoleInputHandler.readString();
		customerController.createCustomer(nameInput, emailInput);
	}
}
