package com.programmers.springbasic.command.voucher;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.programmers.springbasic.command.Command;
import com.programmers.springbasic.console.ConsoleInputHandler;
import com.programmers.springbasic.console.ConsoleOutputHandler;
import com.programmers.springbasic.controller.VoucherController;
import com.programmers.springbasic.entity.voucher.Voucher;

@Component
public class GetVoucherDetailCommand implements Command {

	private final VoucherController voucherController;
	private final ConsoleInputHandler consoleInputHandler;
	private final ConsoleOutputHandler consoleOutputHandler;

	public GetVoucherDetailCommand(VoucherController voucherController, ConsoleInputHandler consoleInputHandler,
		ConsoleOutputHandler consoleOutputHandler) {
		this.voucherController = voucherController;
		this.consoleInputHandler = consoleInputHandler;
		this.consoleOutputHandler = consoleOutputHandler;
	}

	@Override
	public void execute() {
		consoleOutputHandler.printIdPrompt();
		UUID uuidInput = consoleInputHandler.readUUID();
		Voucher voucher = voucherController.getVoucherDetail(uuidInput);
		consoleOutputHandler.printObject(voucher);
	}
}
