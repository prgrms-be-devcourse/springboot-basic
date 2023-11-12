package com.programmers.springbasic.command.voucher;

import java.util.List;

import org.springframework.stereotype.Component;

import com.programmers.springbasic.command.Command;
import com.programmers.springbasic.console.ConsoleOutputHandler;
import com.programmers.springbasic.controller.VoucherRestController;
import com.programmers.springbasic.repository.dto.voucher.VoucherResponse;

@Component
public class GetAllVouchersCommand implements Command {

	private final VoucherRestController voucherRestController;
	private final ConsoleOutputHandler consoleOutputHandler;

	public GetAllVouchersCommand(VoucherRestController voucherRestController, ConsoleOutputHandler consoleOutputHandler) {
		this.voucherRestController = voucherRestController;
		this.consoleOutputHandler = consoleOutputHandler;
	}

	@Override
	public void execute() {
		List<VoucherResponse> vouchers = voucherRestController.getVouchers(null, null, null).getBody();
		consoleOutputHandler.printList(vouchers);
	}
}
