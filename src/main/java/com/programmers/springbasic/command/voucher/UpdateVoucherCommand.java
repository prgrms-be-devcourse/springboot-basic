package com.programmers.springbasic.command.voucher;

import static com.programmers.springbasic.enums.MessageConstants.*;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.programmers.springbasic.command.Command;
import com.programmers.springbasic.console.ConsoleInputHandler;
import com.programmers.springbasic.console.ConsoleOutputHandler;
import com.programmers.springbasic.controller.VoucherController;
import com.programmers.springbasic.entity.voucher.FixedAmountVoucher;
import com.programmers.springbasic.entity.voucher.PercentDiscountVoucher;
import com.programmers.springbasic.entity.voucher.Voucher;

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
		UUID uuidInput = consoleInputHandler.readUUID();

		Voucher voucher = voucherController.getVoucherDetail(uuidInput);
		consoleOutputHandler.print(voucher);

		if (voucher instanceof FixedAmountVoucher) {
			consoleOutputHandler.print(NEW_AMOUNT_PROMPT);
			long newAmount = consoleInputHandler.readLong();
			voucherController.updateVoucher(voucher.getVoucherId(), newAmount);
		} else if (voucher instanceof PercentDiscountVoucher) {
			consoleOutputHandler.print(NEW_PERCENT_PROMPT);
			long newPercent = consoleInputHandler.readLong();
			voucherController.updateVoucher(voucher.getVoucherId(), newPercent);
		}
	}

}
