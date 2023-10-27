package com.programmers.springbasic.command.wallet;

import static com.programmers.springbasic.constants.MessageConstants.*;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.programmers.springbasic.command.Command;
import com.programmers.springbasic.console.ConsoleInputHandler;
import com.programmers.springbasic.console.ConsoleOutputHandler;
import com.programmers.springbasic.controller.CustomerController;
import com.programmers.springbasic.controller.VoucherController;
import com.programmers.springbasic.dto.VoucherDto;

@Component
public class AssignVoucherToCustomerCommand implements Command {

	private final CustomerController customerController;
	private final VoucherController voucherController;
	private final ConsoleInputHandler consoleInputHandler;
	private final ConsoleOutputHandler consoleOutputHandler;

	public AssignVoucherToCustomerCommand(CustomerController customerController, VoucherController voucherController,
		ConsoleInputHandler consoleInputHandler, ConsoleOutputHandler consoleOutputHandler) {
		this.customerController = customerController;
		this.voucherController = voucherController;
		this.consoleInputHandler = consoleInputHandler;
		this.consoleOutputHandler = consoleOutputHandler;
	}

	@Override
	public void execute() {
		consoleOutputHandler.print(CUSTOMER_ID_PROMPT);
		UUID customerId = consoleInputHandler.readUUID();

		List<VoucherDto> vouchers = voucherController.getVouchers();
		consoleOutputHandler.printList(vouchers);

		consoleOutputHandler.print(VOUCHER_ID_PROMPT);
		UUID voucherId = consoleInputHandler.readUUID();

		customerController.assignVoucherToCustomer(customerId, voucherId);
	}
}
