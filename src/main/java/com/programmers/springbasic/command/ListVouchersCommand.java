package com.programmers.springbasic.command;

import java.util.List;

import com.programmers.springbasic.console.ConsoleOutputHandler;
import com.programmers.springbasic.controller.VoucherController;
import com.programmers.springbasic.controller.dto.ListVouchersResponse;

public class ListVouchersCommand implements Command {
	private final VoucherController voucherController;
	private final ConsoleOutputHandler consoleOutputHandler;

	public ListVouchersCommand(VoucherController voucherController, ConsoleOutputHandler consoleOutputHandler) {
		this.voucherController = voucherController;
		this.consoleOutputHandler = consoleOutputHandler;
	}

	@Override
	public void execute() {
		List<ListVouchersResponse> vouchers = voucherController.listVoucher();
		consoleOutputHandler.printVoucherList(vouchers);
	}
}
