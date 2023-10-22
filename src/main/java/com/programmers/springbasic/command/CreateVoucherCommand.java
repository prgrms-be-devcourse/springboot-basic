package com.programmers.springbasic.command;

import com.programmers.springbasic.console.ConsoleInputHandler;
import com.programmers.springbasic.console.ConsoleOutputHandler;
import com.programmers.springbasic.controller.VoucherController;
import com.programmers.springbasic.dto.CreateFixedAmountVoucherRequest;
import com.programmers.springbasic.dto.CreatePercentDiscountVoucherRequest;
import com.programmers.springbasic.entity.voucher.VoucherType;

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
		consoleOutputHandler.printChooseVoucherType();
		VoucherType voucherType = VoucherType.from(consoleInputHandler.readString());
		switch (voucherType) {
			case FIXED_AMOUNT -> {
				CreateFixedAmountVoucherRequest fixedRequest = createFixedAmountVoucherRequest();
				voucherController.createFixedAmountVoucher(fixedRequest);
			}
			case PERCENT_DISCOUNT -> {
				CreatePercentDiscountVoucherRequest percentRequest = createPercentDiscountVoucherRequest();
				voucherController.createPercentDiscountVoucher(percentRequest);
			}
		}
	}

	private CreateFixedAmountVoucherRequest createFixedAmountVoucherRequest() {
		consoleOutputHandler.printFixedAmount();
		long amount = consoleInputHandler.readLong();
		return new CreateFixedAmountVoucherRequest(amount);
	}

	private CreatePercentDiscountVoucherRequest createPercentDiscountVoucherRequest() {
		consoleOutputHandler.printPercentDiscount();
		long percent = consoleInputHandler.readLong();
		return new CreatePercentDiscountVoucherRequest(percent);
	}
}
