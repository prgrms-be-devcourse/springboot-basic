package com.programmers.springbasic.command.voucher;

import static com.programmers.springbasic.constants.MessageConstants.*;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.programmers.springbasic.command.Command;
import com.programmers.springbasic.console.ConsoleInputHandler;
import com.programmers.springbasic.console.ConsoleOutputHandler;
import com.programmers.springbasic.controller.VoucherController;
import com.programmers.springbasic.dto.VoucherDto;
import com.programmers.springbasic.entity.voucher.VoucherType;

@Component
public class UpdateVoucherCommand implements Command {

	private final VoucherController voucherController;
	private final ConsoleInputHandler consoleInputHandler;
	private final ConsoleOutputHandler consoleOutputHandler;

	public UpdateVoucherCommand(VoucherController voucherController, ConsoleInputHandler consoleInputHandler,
		ConsoleOutputHandler consoleOutputHandler) {
		this.voucherController = voucherController;
		this.consoleInputHandler = consoleInputHandler;
		this.consoleOutputHandler = consoleOutputHandler;
	}

	@Override
	public void execute() {
		consoleOutputHandler.print(VOUCHER_ID_PROMPT);
		UUID voucherId = consoleInputHandler.readUUID();

		VoucherDto voucher = voucherController.getVoucherDetail(voucherId);
		consoleOutputHandler.print(voucher);

		if (voucher.voucherType() == VoucherType.FIXED_AMOUNT) {
			consoleOutputHandler.print(NEW_AMOUNT_PROMPT);
			long newAmount = consoleInputHandler.readLong();
			voucherController.updateVoucher(voucherId, newAmount);
		} else if (voucher.voucherType() == VoucherType.PERCENT_DISCOUNT) {
			consoleOutputHandler.print(NEW_PERCENT_PROMPT);
			long newPercent = consoleInputHandler.readLong();
			voucherController.updateVoucher(voucherId, newPercent);
		}
	}

}
