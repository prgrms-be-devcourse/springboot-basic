package com.programmers.springbasic.command.wallet;

import static com.programmers.springbasic.constants.MessageConstants.*;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.programmers.springbasic.command.Command;
import com.programmers.springbasic.console.ConsoleInputHandler;
import com.programmers.springbasic.console.ConsoleOutputHandler;
import com.programmers.springbasic.controller.VoucherController;
import com.programmers.springbasic.dto.CustomerDto;

@Component
public class GetCustomersByVoucher implements Command {
	private final VoucherController voucherController;
	private final ConsoleInputHandler consoleInputHandler;
	private final ConsoleOutputHandler consoleOutputHandler;

	public GetCustomersByVoucher(VoucherController voucherController, ConsoleInputHandler consoleInputHandler,
		ConsoleOutputHandler consoleOutputHandler) {
		this.voucherController = voucherController;
		this.consoleInputHandler = consoleInputHandler;
		this.consoleOutputHandler = consoleOutputHandler;
	}

	@Override
	public void execute() {
		consoleOutputHandler.print(VOUCHER_ID_PROMPT);
		UUID uuidInput = consoleInputHandler.readUUID();
		List<CustomerDto> customerList = voucherController.getCustomersByVoucher(uuidInput);
		consoleOutputHandler.printList(customerList);
	}
}
