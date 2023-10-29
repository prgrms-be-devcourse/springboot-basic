package com.programmers.springbasic.command.customer;

import java.util.List;

import org.springframework.stereotype.Component;

import com.programmers.springbasic.command.Command;
import com.programmers.springbasic.console.ConsoleOutputHandler;
import com.programmers.springbasic.controller.CustomerController;
import com.programmers.springbasic.dto.CustomerDto;

@Component
public class GetAllCustomersCommand implements Command {

	private final CustomerController customerController;
	private final ConsoleOutputHandler consoleOutputHandler;

	public GetAllCustomersCommand(CustomerController customerController, ConsoleOutputHandler consoleOutputHandler) {
		this.customerController = customerController;
		this.consoleOutputHandler = consoleOutputHandler;
	}

	@Override
	public void execute() {
		List<CustomerDto> customers = customerController.getAllCustomers();
		consoleOutputHandler.printList(customers);
	}
}
