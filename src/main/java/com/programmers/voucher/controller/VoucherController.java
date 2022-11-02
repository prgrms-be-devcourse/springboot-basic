package com.programmers.voucher.controller;

import java.util.List;

import com.programmers.voucher.domain.Voucher;
import com.programmers.voucher.io.Input;
import com.programmers.voucher.io.Output;
import com.programmers.voucher.service.VoucherService;

public class VoucherController implements Runnable {

	private Input input;
	private Output output;
	private VoucherService service;

	public VoucherController(Input input, Output output, VoucherService service) {
		this.input = input;
		this.output = output;
		this.service = service;
	}

	@Override
	public void run() {
		while (true) {
			try {
				output.write(Message.COMMAND_OPTION.getMessage());
				Command chosenCommand = Command.getCommand(input.read());

				switch (chosenCommand) {
					case CREATE -> createVoucher();
					case LIST -> writeAllVoucher();
					case EXIT -> {
						return;
					}
				}
			} catch (RuntimeException e) {
				output.write(e.getMessage());
			}
		}
	}

	private void createVoucher() {
		output.write(Message.VOUCHER_OPTION.getMessage());
		String chosenVoucher = input.read();
		output.write(Message.DISCOUNT_OPTION.getMessage());
		int discountAmount = Integer.parseInt(input.read());
		service.createVoucher(chosenVoucher, discountAmount);
	}

	public void writeAllVoucher() {
		List<Voucher> vouchers = service.getAllVoucher();
		vouchers.stream()
			.forEach(i -> output.write(i.toString()));
	}
}
