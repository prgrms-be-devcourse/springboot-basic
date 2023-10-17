package com.programmers.springbasic.command;

import java.util.List;

import com.programmers.springbasic.console.ConsoleOutputHandler;
import com.programmers.springbasic.controller.CustomerController;
import com.programmers.springbasic.dto.ListBlacklistCustomerResponse;

public class ListBlacklistCustomersCommand implements Command {
	private final CustomerController customerController;
	private final ConsoleOutputHandler consoleOutputHandler;

	public ListBlacklistCustomersCommand(CustomerController customerController,
		ConsoleOutputHandler consoleOutputHandler) {
		this.customerController = customerController;
		this.consoleOutputHandler = consoleOutputHandler;
	}

	@Override
	public void execute() {
		List<ListBlacklistCustomerResponse> blacklistCustomers = customerController.listBlacklistCustomer();
		consoleOutputHandler.printList(blacklistCustomers);
	}
}
