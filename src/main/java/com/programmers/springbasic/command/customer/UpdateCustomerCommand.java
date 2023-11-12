package com.programmers.springbasic.command.customer;

import static com.programmers.springbasic.console.constants.MessageConstants.*;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.programmers.springbasic.command.Command;
import com.programmers.springbasic.console.ConsoleInputHandler;
import com.programmers.springbasic.console.ConsoleOutputHandler;
import com.programmers.springbasic.controller.CustomerRestController;
import com.programmers.springbasic.repository.dto.customer.CustomerResponse;
import com.programmers.springbasic.repository.dto.customer.UpdateCustomerRequest;

@Component
public class UpdateCustomerCommand implements Command {

	private final CustomerRestController customerRestController;
	private final ConsoleInputHandler consoleInputHandler;
	private final ConsoleOutputHandler consoleOutputHandler;

	public UpdateCustomerCommand(CustomerRestController customerRestController, ConsoleInputHandler consoleInputHandler,
		ConsoleOutputHandler consoleOutputHandler) {
		this.customerRestController = customerRestController;
		this.consoleInputHandler = consoleInputHandler;
		this.consoleOutputHandler = consoleOutputHandler;
	}

	@Override
	public void execute() {
		consoleOutputHandler.print(CUSTOMER_ID_PROMPT);
		UUID uuidInput = consoleInputHandler.readUUID();
		consoleOutputHandler.print(NAME_PROMPT);
		String nameInput = consoleInputHandler.readString();
		CustomerResponse updatedCustomer = customerRestController.updateCustomer(uuidInput, new UpdateCustomerRequest(nameInput)).getBody();
		consoleOutputHandler.printWithLineBreak(updatedCustomer);
	}
}
