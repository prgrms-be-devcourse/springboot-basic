package com.programmers.basic.command;

import com.programmers.basic.console.InputHandler;
import com.programmers.basic.console.OutputHandler;
import com.programmers.basic.controller.VoucherController;
import com.programmers.basic.controller.dto.CreateFixedAmountVoucherRequest;
import com.programmers.basic.controller.dto.CreatePercentDiscountVoucherRequest;
import com.programmers.basic.entity.VoucherType;

public class CreateVoucherCommand implements Command {
	private final VoucherController voucherController;
	private final InputHandler inputHandler;
	private final OutputHandler outputHandler;

	public CreateVoucherCommand(VoucherController voucherController, InputHandler inputHandler,
		OutputHandler outputHandler) {
		this.voucherController = voucherController;
		this.inputHandler = inputHandler;
		this.outputHandler = outputHandler;
	}

	@Override
	public void execute() {
		outputHandler.printChooseVoucherType();
		VoucherType voucherType = VoucherType.valueOf(inputHandler.readString());
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
		outputHandler.printFixedAmount();
		long amount = inputHandler.readLong();
		return new CreateFixedAmountVoucherRequest(amount);
	}

	private CreatePercentDiscountVoucherRequest createPercentDiscountVoucherRequest() {
		outputHandler.printPercentDiscount();
		long percent = inputHandler.readLong();
		return new CreatePercentDiscountVoucherRequest(percent);
	}
}
