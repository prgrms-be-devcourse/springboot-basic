package com.programmers.voucher.console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.programmers.voucher.console.io.Input;
import com.programmers.voucher.console.io.Output;
import com.programmers.voucher.console.run.AppPower;
import com.programmers.voucher.console.run.Command;
import com.programmers.voucher.console.run.Message;
import com.programmers.voucher.domain.customer.service.CustomerService;
import com.programmers.voucher.domain.voucher.service.VoucherService;
import com.programmers.voucher.domain.wallet.service.WalletService;

@Controller
public class ConsoleVoucherApp implements Runnable {

	private final Input input;
	private final Output output;
	private final VoucherService voucherService;
	private final CustomerService customerService;
	private final WalletService walletService;

	@Autowired
	public ConsoleVoucherApp(Input input, Output output, VoucherService voucherService,
		CustomerService customerService, WalletService walletService) {
		this.input = input;
		this.output = output;
		this.voucherService = voucherService;
		this.customerService = customerService;
		this.walletService = walletService;
	}

	@Override
	public void run() {
		while (AppPower.isRunning()) {
			try {
				output.write(Message.COMMAND_OPTION.getMessage());
				Command chosenCommand = Command.getCommand(input.read());
				chosenCommand.doCommand(input, output, voucherService, customerService, walletService);
			} catch (RuntimeException e) {
				output.write(e.getMessage());
			}
		}
	}
}
