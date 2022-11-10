package com.programmers.voucher.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.programmers.voucher.domain.customer.model.Customer;
import com.programmers.voucher.domain.customer.service.CustomerService;
import com.programmers.voucher.domain.voucher.model.Voucher;
import com.programmers.voucher.domain.voucher.model.VoucherType;
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
		VoucherType choseVoucherType = VoucherType.getVoucherType(input.read());
		output.write(Message.DISCOUNT_OPTION.getMessage());
		String discountAmount = input.read();
		voucherService.createVoucher(choseVoucherType, discountAmount);
	}

	public void writeAllVoucher() {
		List<Voucher> vouchers = voucherService.getAllVoucher();
		vouchers.forEach(voucher -> output.write(voucher.toString()));
	}

	public void writeAllBlacklist() {
		List<Customer> customers = customerService.getBlackList();
		customers.forEach(blacklist -> output.write(blacklist.toString()));
	}
}
