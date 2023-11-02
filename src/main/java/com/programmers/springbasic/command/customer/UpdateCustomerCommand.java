package com.programmers.springbasic.command.customer;

import static com.programmers.springbasic.console.constants.MessageConstants.*;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.programmers.springbasic.command.Command;
import com.programmers.springbasic.console.ConsoleInputHandler;
import com.programmers.springbasic.console.ConsoleOutputHandler;
import com.programmers.springbasic.controller.CustomerController;
import com.programmers.springbasic.dto.CustomerDto;

@Component
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
		consoleOutputHandler.print(CUSTOMER_ID_PROMPT);
		UUID uuidInput = consoleInputHandler.readUUID();
		consoleOutputHandler.print(NAME_PROMPT);
		String nameInput = consoleInputHandler.readString();
		CustomerDto updatedCustomer = customerController.updateCustomer(uuidInput, nameInput);
		consoleOutputHandler.printWithLineBreak(updatedCustomer);
	}
}
