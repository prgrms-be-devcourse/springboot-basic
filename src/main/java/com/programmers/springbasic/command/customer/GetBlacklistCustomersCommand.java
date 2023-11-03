package com.programmers.springbasic.command.customer;

import java.util.List;

import org.springframework.stereotype.Component;

import com.programmers.springbasic.command.Command;
import com.programmers.springbasic.console.ConsoleOutputHandler;
import com.programmers.springbasic.controller.CustomerRestController;
import com.programmers.springbasic.repository.dto.customer.CustomerResponse;

@Component
public class GetBlacklistCustomersCommand implements Command {

	private final CustomerRestController customerRestController;
	private final ConsoleOutputHandler consoleOutputHandler;

	public GetBlacklistCustomersCommand(CustomerRestController customerRestController,
		ConsoleOutputHandler consoleOutputHandler) {
		this.customerRestController = customerRestController;
		this.consoleOutputHandler = consoleOutputHandler;
	}

	@Override
	public void execute() {
		List<CustomerResponse> blacklistCustomers = customerRestController.getBlacklistCustomers().getBody();
		consoleOutputHandler.printList(blacklistCustomers);
	}
}
