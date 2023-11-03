package com.programmers.springbasic.command.wallet;

import static com.programmers.springbasic.console.constants.MessageConstants.*;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.programmers.springbasic.command.Command;
import com.programmers.springbasic.console.ConsoleInputHandler;
import com.programmers.springbasic.console.ConsoleOutputHandler;
import com.programmers.springbasic.controller.CustomerRestController;
import com.programmers.springbasic.controller.VoucherRestController;
import com.programmers.springbasic.repository.dto.voucher.VoucherResponse;

@Component
public class AssignVoucherToCustomerCommand implements Command {

	private final CustomerRestController customerRestController;
	private final VoucherRestController voucherRestController;
	private final ConsoleInputHandler consoleInputHandler;
	private final ConsoleOutputHandler consoleOutputHandler;

	public AssignVoucherToCustomerCommand(CustomerRestController customerRestController, VoucherRestController voucherRestController,
		ConsoleInputHandler consoleInputHandler, ConsoleOutputHandler consoleOutputHandler) {
		this.customerRestController = customerRestController;
		this.voucherRestController = voucherRestController;
		this.consoleInputHandler = consoleInputHandler;
		this.consoleOutputHandler = consoleOutputHandler;
	}

	@Override
	public void execute() {
		consoleOutputHandler.print(CUSTOMER_ID_PROMPT);
		UUID customerId = consoleInputHandler.readUUID();

		List<VoucherResponse> vouchers = voucherRestController.getVouchers(null, null, null).getBody();
		consoleOutputHandler.printList(vouchers);

		consoleOutputHandler.print(VOUCHER_ID_PROMPT);
		UUID voucherId = consoleInputHandler.readUUID();

		customerRestController.assignVoucherToCustomer(customerId, voucherId);
	}
}
