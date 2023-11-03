package com.programmers.springbasic.command.voucher;

import static com.programmers.springbasic.console.constants.MessageConstants.*;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.programmers.springbasic.command.Command;
import com.programmers.springbasic.console.ConsoleInputHandler;
import com.programmers.springbasic.console.ConsoleOutputHandler;
import com.programmers.springbasic.controller.VoucherRestController;
import com.programmers.springbasic.repository.dto.voucher.VoucherResponse;

@Component
public class GetVoucherDetailCommand implements Command {

	private final VoucherRestController voucherRestController;
	private final ConsoleInputHandler consoleInputHandler;
	private final ConsoleOutputHandler consoleOutputHandler;

	public GetVoucherDetailCommand(VoucherRestController voucherRestController, ConsoleInputHandler consoleInputHandler,
		ConsoleOutputHandler consoleOutputHandler) {
		this.voucherRestController = voucherRestController;
		this.consoleInputHandler = consoleInputHandler;
		this.consoleOutputHandler = consoleOutputHandler;
	}

	@Override
	public void execute() {
		consoleOutputHandler.print(VOUCHER_ID_PROMPT);
		UUID uuidInput = consoleInputHandler.readUUID();
		VoucherResponse voucher = voucherRestController.getVoucherDetail(uuidInput).getBody();
		consoleOutputHandler.printWithLineBreak(voucher);
	}
}
