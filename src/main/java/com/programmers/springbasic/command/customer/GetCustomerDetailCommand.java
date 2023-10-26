package com.programmers.springbasic.command.customer;

import org.springframework.stereotype.Component;

import com.programmers.springbasic.command.Command;
import com.programmers.springbasic.console.ConsoleInputHandler;
import com.programmers.springbasic.console.ConsoleOutputHandler;
import com.programmers.springbasic.controller.CustomerController;
import com.programmers.springbasic.entity.customer.Customer;

@Component
public class GetCustomerDetailCommand implements Command {

	private final CustomerController customerController;
	private final ConsoleInputHandler consoleInputHandler;
	private final ConsoleOutputHandler consoleOutputHandler;

	public GetCustomerDetailCommand(CustomerController customerController, ConsoleInputHandler consoleInputHandler,
		ConsoleOutputHandler consoleOutputHandler) {
		this.customerController = customerController;
		this.consoleInputHandler = consoleInputHandler;
		this.consoleOutputHandler = consoleOutputHandler;
	}

	@Override
	public void execute() {
		consoleOutputHandler.printIdPrompt();
		String uuidInput = consoleInputHandler.readString();
		Customer customer = customerController.getCustomerById(uuidInput);
		consoleOutputHandler.printObject(customer);
	}
}
