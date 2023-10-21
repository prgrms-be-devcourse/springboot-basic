package com.programmers.springbasic.command;

import java.util.List;

import com.programmers.springbasic.console.ConsoleOutputHandler;
import com.programmers.springbasic.controller.CustomerController;
import com.programmers.springbasic.dto.GetBlacklistCustomersResponse;

public class GetBlacklistCustomersCommand implements Command {

	private final CustomerController customerController;
	private final ConsoleOutputHandler consoleOutputHandler;

	public GetBlacklistCustomersCommand(CustomerController customerController,
		ConsoleOutputHandler consoleOutputHandler) {
		this.customerController = customerController;
		this.consoleOutputHandler = consoleOutputHandler;
	}

	@Override
	public void execute() {
		List<GetBlacklistCustomersResponse> blacklistCustomers = customerController.getBlacklistCustomers();
		consoleOutputHandler.printList(blacklistCustomers);
	}
}
