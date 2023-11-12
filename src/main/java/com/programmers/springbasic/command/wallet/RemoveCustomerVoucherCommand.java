package com.programmers.springbasic.command.wallet;

import static com.programmers.springbasic.console.constants.MessageConstants.*;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.programmers.springbasic.command.Command;
import com.programmers.springbasic.console.ConsoleInputHandler;
import com.programmers.springbasic.console.ConsoleOutputHandler;
import com.programmers.springbasic.controller.CustomerRestController;

@Component
public class RemoveCustomerVoucherCommand implements Command {

	private final CustomerRestController customerRestController;
	private final ConsoleInputHandler consoleInputHandler;
	private final ConsoleOutputHandler consoleOutputHandler;

	public RemoveCustomerVoucherCommand(CustomerRestController customerRestController, ConsoleInputHandler consoleInputHandler,
		ConsoleOutputHandler consoleOutputHandler) {
		this.customerRestController = customerRestController;
		this.consoleInputHandler = consoleInputHandler;
		this.consoleOutputHandler = consoleOutputHandler;
	}

	@Override
	public void execute() {
		consoleOutputHandler.print(CUSTOMER_ID_PROMPT);
		UUID customerId = consoleInputHandler.readUUID();
		consoleOutputHandler.print(VOUCHER_ID_PROMPT);
		UUID voucherId = consoleInputHandler.readUUID();
		customerRestController.removeVoucherFromCustomer(customerId, voucherId);
	}
}
