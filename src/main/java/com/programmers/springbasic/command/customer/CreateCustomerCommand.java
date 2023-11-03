package com.programmers.springbasic.command.customer;

import static com.programmers.springbasic.console.constants.MessageConstants.*;

import org.springframework.stereotype.Component;

import com.programmers.springbasic.command.Command;
import com.programmers.springbasic.console.ConsoleInputHandler;
import com.programmers.springbasic.console.ConsoleOutputHandler;
import com.programmers.springbasic.controller.CustomerRestController;
import com.programmers.springbasic.repository.dto.customer.CreateCustomerRequest;

@Component
public class CreateCustomerCommand implements Command {

	private final CustomerRestController customerRestController;
	private final ConsoleInputHandler consoleInputHandler;
	private final ConsoleOutputHandler consoleOutputHandler;

	public CreateCustomerCommand(CustomerRestController customerRestController, ConsoleInputHandler consoleInputHandler,
		ConsoleOutputHandler consoleOutputHandler) {
		this.customerRestController = customerRestController;
		this.consoleInputHandler = consoleInputHandler;
		this.consoleOutputHandler = consoleOutputHandler;
	}

	@Override
	public void execute() {
		consoleOutputHandler.print(NAME_PROMPT);
		String nameInput = consoleInputHandler.readString();
		consoleOutputHandler.print(EMAIL_PROMPT);
		String emailInput = consoleInputHandler.readString();
		customerRestController.createCustomer(new CreateCustomerRequest(nameInput, emailInput));
	}
}
