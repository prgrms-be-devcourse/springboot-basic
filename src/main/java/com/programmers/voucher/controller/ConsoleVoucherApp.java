package com.programmers.voucher.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.programmers.voucher.domain.customer.service.CustomerService;
import com.programmers.voucher.domain.voucher.service.VoucherService;
import com.programmers.voucher.domain.wallet.service.WalletService;
import com.programmers.voucher.io.Input;
import com.programmers.voucher.io.Output;

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
		while (ControllerPower.isRunning()) {
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
