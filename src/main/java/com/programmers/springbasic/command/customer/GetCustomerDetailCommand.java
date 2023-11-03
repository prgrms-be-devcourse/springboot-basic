package com.programmers.springbasic.command.customer;

import static com.programmers.springbasic.console.constants.MessageConstants.*;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.programmers.springbasic.command.Command;
import com.programmers.springbasic.console.ConsoleInputHandler;
import com.programmers.springbasic.console.ConsoleOutputHandler;
import com.programmers.springbasic.controller.CustomerRestController;
import com.programmers.springbasic.repository.dto.customer.CustomerResponse;

@Component
public class GetCustomerDetailCommand implements Command {

	private final CustomerRestController customerRestController;
	private final ConsoleInputHandler consoleInputHandler;
	private final ConsoleOutputHandler consoleOutputHandler;

	public GetCustomerDetailCommand(CustomerRestController customerRestController, ConsoleInputHandler consoleInputHandler,
		ConsoleOutputHandler consoleOutputHandler) {
		this.customerRestController = customerRestController;
		this.consoleInputHandler = consoleInputHandler;
		this.consoleOutputHandler = consoleOutputHandler;
	}

	@Override
	public void execute() {
		consoleOutputHandler.print(CUSTOMER_ID_PROMPT);
		UUID uuidInput = consoleInputHandler.readUUID();
		CustomerResponse customer = customerRestController.getCustomerById(uuidInput).getBody();
		consoleOutputHandler.printWithLineBreak(customer);
	}
}
