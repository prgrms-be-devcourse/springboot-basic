package com.programmers.springbasic.command.voucher;

import static com.programmers.springbasic.console.constants.MessageConstants.*;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.programmers.springbasic.command.Command;
import com.programmers.springbasic.console.ConsoleInputHandler;
import com.programmers.springbasic.console.ConsoleOutputHandler;
import com.programmers.springbasic.controller.VoucherRestController;
import com.programmers.springbasic.repository.dto.voucher.UpdateVoucherRequest;
import com.programmers.springbasic.repository.dto.voucher.VoucherResponse;
import com.programmers.springbasic.entity.voucher.VoucherType;

@Component
public class UpdateVoucherCommand implements Command {

	private final VoucherRestController voucherRestController;
	private final ConsoleInputHandler consoleInputHandler;
	private final ConsoleOutputHandler consoleOutputHandler;

	public UpdateVoucherCommand(VoucherRestController voucherRestController, ConsoleInputHandler consoleInputHandler,
		ConsoleOutputHandler consoleOutputHandler) {
		this.voucherRestController = voucherRestController;
		this.consoleInputHandler = consoleInputHandler;
		this.consoleOutputHandler = consoleOutputHandler;
	}

	@Override
	public void execute() {
		consoleOutputHandler.print(VOUCHER_ID_PROMPT);
		UUID voucherId = consoleInputHandler.readUUID();

		VoucherResponse voucher = voucherRestController.getVoucherDetail(voucherId).getBody();
		consoleOutputHandler.printWithLineBreak(voucher);

		if (voucher.voucherType() == VoucherType.FIXED_AMOUNT) {
			consoleOutputHandler.print(NEW_AMOUNT_PROMPT);
			long newAmount = consoleInputHandler.readLong();
			VoucherResponse updatedVoucher = voucherRestController.updateVoucher(voucherId,
				new UpdateVoucherRequest(newAmount)).getBody();
			consoleOutputHandler.printWithLineBreak(updatedVoucher);
		} else if (voucher.voucherType() == VoucherType.PERCENT_DISCOUNT) {
			consoleOutputHandler.print(NEW_PERCENT_PROMPT);
			long newPercent = consoleInputHandler.readLong();
			VoucherResponse updatedVoucher = voucherRestController.updateVoucher(voucherId,
				new UpdateVoucherRequest(newPercent)).getBody();
			consoleOutputHandler.printWithLineBreak(updatedVoucher);
		}
	}

}
