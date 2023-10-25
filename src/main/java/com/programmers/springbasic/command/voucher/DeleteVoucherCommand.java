package com.programmers.springbasic.command.voucher;

import com.programmers.springbasic.command.Command;
import com.programmers.springbasic.console.ConsoleInputHandler;
import com.programmers.springbasic.console.ConsoleOutputHandler;
import com.programmers.springbasic.controller.VoucherController;

public class DeleteVoucherCommand implements Command {

	private final VoucherController voucherController;
	private final ConsoleInputHandler consoleInputHandler;
	private final ConsoleOutputHandler consoleOutputHandler;

	public DeleteVoucherCommand(VoucherController voucherController, ConsoleInputHandler consoleInputHandler,
		ConsoleOutputHandler consoleOutputHandler) {
		this.voucherController = voucherController;
		this.consoleInputHandler = consoleInputHandler;
		this.consoleOutputHandler = consoleOutputHandler;
	}

	@Override
	public void execute() {
		consoleOutputHandler.printIdPrompt();
		String UuidInput = consoleInputHandler.readString();
		voucherController.deleteVoucher(UuidInput);
	}
}
