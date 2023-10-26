package com.programmers.springbasic.command.voucher;

import java.util.List;

import org.springframework.stereotype.Component;

import com.programmers.springbasic.command.Command;
import com.programmers.springbasic.console.ConsoleOutputHandler;
import com.programmers.springbasic.controller.VoucherController;
import com.programmers.springbasic.dto.VoucherDto;

@Component
public class GetAllVouchersCommand implements Command {

	private final VoucherController voucherController;
	private final ConsoleOutputHandler consoleOutputHandler;

	public GetAllVouchersCommand(VoucherController voucherController, ConsoleOutputHandler consoleOutputHandler) {
		this.voucherController = voucherController;
		this.consoleOutputHandler = consoleOutputHandler;
	}

	@Override
	public void execute() {
		List<VoucherDto> vouchers = voucherController.getVouchers();
		consoleOutputHandler.printList(vouchers);
	}
}
