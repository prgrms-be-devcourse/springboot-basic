package com.programmers.springbasic.command.wallet;

import static com.programmers.springbasic.console.constants.MessageConstants.*;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.programmers.springbasic.command.Command;
import com.programmers.springbasic.console.ConsoleInputHandler;
import com.programmers.springbasic.console.ConsoleOutputHandler;
import com.programmers.springbasic.controller.VoucherRestController;
import com.programmers.springbasic.repository.dto.customer.CustomerResponse;

@Component
public class GetCustomersByVoucherCommand implements Command {
	private final VoucherRestController voucherRestController;
	private final ConsoleInputHandler consoleInputHandler;
	private final ConsoleOutputHandler consoleOutputHandler;

	public GetCustomersByVoucherCommand(VoucherRestController voucherRestController, ConsoleInputHandler consoleInputHandler,
		ConsoleOutputHandler consoleOutputHandler) {
		this.voucherRestController = voucherRestController;
		this.consoleInputHandler = consoleInputHandler;
		this.consoleOutputHandler = consoleOutputHandler;
	}

	@Override
	public void execute() {
		consoleOutputHandler.print(VOUCHER_ID_PROMPT);
		UUID uuidInput = consoleInputHandler.readUUID();
		List<CustomerResponse> customerList = voucherRestController.getCustomersByVoucher(uuidInput).getBody();
		consoleOutputHandler.printList(customerList);
	}
}
