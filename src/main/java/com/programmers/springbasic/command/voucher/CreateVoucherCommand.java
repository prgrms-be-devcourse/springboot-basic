package com.programmers.springbasic.command.voucher;

import static com.programmers.springbasic.enums.MessageConstants.*;

import org.springframework.stereotype.Component;

import com.programmers.springbasic.command.Command;
import com.programmers.springbasic.console.ConsoleInputHandler;
import com.programmers.springbasic.console.ConsoleOutputHandler;
import com.programmers.springbasic.controller.VoucherController;
import com.programmers.springbasic.entity.voucher.VoucherType;

@Component
public class CreateVoucherCommand implements Command {

	private final VoucherController voucherController;
	private final ConsoleInputHandler consoleInputHandler;
	private final ConsoleOutputHandler consoleOutputHandler;

	public CreateVoucherCommand(VoucherController voucherController, ConsoleInputHandler consoleInputHandler,
		ConsoleOutputHandler consoleOutputHandler) {
		this.voucherController = voucherController;
		this.consoleInputHandler = consoleInputHandler;
		this.consoleOutputHandler = consoleOutputHandler;
	}

	@Override
	public void execute() {
		consoleOutputHandler.print(VOUCHER_TYPE_PROMPT);
		VoucherType voucherType = VoucherType.from(consoleInputHandler.readString());
		switch (voucherType) {
			case FIXED_AMOUNT -> {
				consoleOutputHandler.print(AMOUNT_PROMPT);
				long amount = consoleInputHandler.readLong();
				voucherController.createVoucher(voucherType, amount);
			}
			case PERCENT_DISCOUNT -> {
				consoleOutputHandler.print(PERCENT_PROMPT);
				long percent = consoleInputHandler.readLong();
				voucherController.createVoucher(voucherType, percent);
			}
		}
	}

}
