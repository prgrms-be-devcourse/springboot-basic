package com.programmers.voucher.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.programmers.voucher.domain.customer.service.CustomerService;
import com.programmers.voucher.domain.voucher.service.VoucherService;
import com.programmers.voucher.io.Input;
import com.programmers.voucher.io.Output;

@Controller
public class VoucherController implements Runnable {

	private final Input input;
	private final Output output;
	private final VoucherService voucherService;
	private final CustomerService customerService;

	@Autowired
	public VoucherController(Input input, Output output, VoucherService voucherService,
		CustomerService customerService) {
		this.input = input;
		this.output = output;
		this.voucherService = voucherService;
		this.customerService = customerService;
	}

	@Override
	public void run() {
		while (ControllerPower.isRunning()) {
			try {
				output.write(Message.COMMAND_OPTION.getMessage());
				Command chosenCommand = Command.getCommand(input.read());
				chosenCommand.doCommand(input, output, voucherService, customerService);
			} catch (RuntimeException e) {
				output.write(e.getMessage());
			}
		}
	}
}
