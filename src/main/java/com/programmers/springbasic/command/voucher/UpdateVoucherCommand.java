package com.programmers.springbasic.command.voucher;

import java.util.Optional;

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
		consoleOutputHandler.printIdPrompt();
		String uuidInput = consoleInputHandler.readString();

		// Voucher 조회 로직 (voucherController 혹은 적절한 서비스를 사용)
		Voucher voucher = voucherController.getVoucherDetail(uuidInput);

		if (voucher instanceof FixedAmountVoucher) {
			consoleOutputHandler.printObject("Enter new amount for FixedAmountVoucher: ");
			long newAmount = consoleInputHandler.readLong();
			voucherController.updateVoucher(voucher.getVoucherId(), newAmount);
		} else if (voucher instanceof PercentDiscountVoucher) {
			consoleOutputHandler.printObject("Enter new percentage for PercentDiscountVoucher: ");
			long newPercent = consoleInputHandler.readLong();
			voucher.changeDiscountValue(newPercent);
			voucherController.updateVoucher(voucher.getVoucherId(), newPercent);
		}
	}

}
