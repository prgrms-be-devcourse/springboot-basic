package com.programmers.basic.command;

import java.util.List;

import com.programmers.basic.console.OutputHandler;
import com.programmers.basic.controller.VoucherController;
import com.programmers.basic.controller.dto.ListVouchersResponse;

public class ListVouchersCommand implements Command {
	private final VoucherController voucherController;
	private final OutputHandler outputHandler;

	public ListVouchersCommand(VoucherController voucherController, OutputHandler outputHandler) {
		this.voucherController = voucherController;
		this.outputHandler = outputHandler;
	}

	@Override
	public void execute() {
		List<ListVouchersResponse> vouchers = voucherController.listVoucher();
		outputHandler.printVoucherList(vouchers);
	}
}
