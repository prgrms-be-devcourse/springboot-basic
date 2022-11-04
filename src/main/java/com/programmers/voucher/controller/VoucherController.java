package com.programmers.voucher.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.programmers.voucher.domain.voucher.Voucher;
import com.programmers.voucher.domain.customer.Customer;
import com.programmers.voucher.io.Input;
import com.programmers.voucher.io.Output;
import com.programmers.voucher.service.CustomerService;
import com.programmers.voucher.service.VoucherService;

@Controller
public class VoucherController implements Runnable {

	private Input input;
	private Output output;
	private VoucherService voucherService;
	private CustomerService customerService;

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
		while (true) {
			try {
				output.write(Message.COMMAND_OPTION.getMessage());
				Command chosenCommand = Command.getCommand(input.read());

				switch (chosenCommand) {
					case CREATE -> createVoucher();
					case LIST -> writeAllVoucher();
					case BLACKLIST -> writeAllBlacklist();
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
		voucherService.createVoucher(chosenVoucher, discountAmount);
	}

	public void writeAllVoucher() {
		List<Voucher> vouchers = voucherService.getAllVoucher();
		vouchers.stream()
			.forEach(i -> output.write(i.toString()));
	}

	public void writeAllBlacklist() {
		List<Customer> customers = customerService.getBlackList();
		customers.stream()
			.forEach(i -> output.write(i.toString()));
	}
}
