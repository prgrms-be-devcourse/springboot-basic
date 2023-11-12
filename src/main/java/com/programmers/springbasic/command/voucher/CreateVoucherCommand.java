package com.programmers.springbasic.command.voucher;

import static com.programmers.springbasic.console.constants.MessageConstants.*;

import org.springframework.stereotype.Component;

import com.programmers.springbasic.command.Command;
import com.programmers.springbasic.console.ConsoleInputHandler;
import com.programmers.springbasic.console.ConsoleOutputHandler;
import com.programmers.springbasic.controller.VoucherRestController;
import com.programmers.springbasic.repository.dto.voucher.CreateVoucherRequest;
import com.programmers.springbasic.entity.voucher.VoucherType;

@Component
public class CreateVoucherCommand implements Command {

	private final VoucherRestController voucherRestController;
	private final ConsoleInputHandler consoleInputHandler;
	private final ConsoleOutputHandler consoleOutputHandler;

	public CreateVoucherCommand(VoucherRestController voucherRestController, ConsoleInputHandler consoleInputHandler,
		ConsoleOutputHandler consoleOutputHandler) {
		this.voucherRestController = voucherRestController;
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
				voucherRestController.createVoucher(new CreateVoucherRequest(voucherType, amount));
			}
			case PERCENT_DISCOUNT -> {
				consoleOutputHandler.print(PERCENT_PROMPT);
				long percent = consoleInputHandler.readLong();
				voucherRestController.createVoucher(new CreateVoucherRequest(voucherType, percent));
			}
		}
	}

}
