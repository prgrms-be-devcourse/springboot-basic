package com.programmers.springbasic.command.wallet;

import java.util.List;

import org.springframework.stereotype.Component;

import com.programmers.springbasic.command.Command;
import com.programmers.springbasic.console.ConsoleInputHandler;
import com.programmers.springbasic.console.ConsoleOutputHandler;
import com.programmers.springbasic.controller.CustomerController;
import com.programmers.springbasic.entity.customer.Customer;

@Component
public class GetCustomersByVoucher implements Command {
	private final CustomerController customerController;
	private final ConsoleInputHandler consoleInputHandler;
	private final ConsoleOutputHandler consoleOutputHandler;

	public GetCustomersByVoucher(CustomerController customerController, ConsoleInputHandler consoleInputHandler,
		ConsoleOutputHandler consoleOutputHandler) {
		this.customerController = customerController;
		this.consoleInputHandler = consoleInputHandler;
		this.consoleOutputHandler = consoleOutputHandler;
	}

	@Override
	public void execute() {
		consoleOutputHandler.printObject("바우처 아이디 : ");
		String voucherId = consoleInputHandler.readString();
		List<Customer> customerList = customerController.getCustomersByVoucher(voucherId);
		consoleOutputHandler.printList(customerList);
	}
}
