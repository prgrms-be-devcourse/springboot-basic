package com.programmers.voucher.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.programmers.voucher.domain.customer.model.Customer;
import com.programmers.voucher.domain.customer.service.CustomerService;
import com.programmers.voucher.domain.voucher.model.Voucher;
import com.programmers.voucher.domain.voucher.service.VoucherService;
import com.programmers.voucher.domain.voucher.util.Validator;
import com.programmers.voucher.io.Input;
import com.programmers.voucher.io.Output;

@Controller
public class VoucherController implements Runnable {

	private final Logger log = LoggerFactory.getLogger(VoucherController.class);
	private final Input input;
	private final Output output;
	private final VoucherService voucherService;
	private final CustomerService customerService;
	private final Validator validator;

	@Autowired
	public VoucherController(Input input, Output output, VoucherService voucherService,
		CustomerService customerService, Validator validator) {
		this.input = input;
		this.output = output;
		this.voucherService = voucherService;
		this.customerService = customerService;
		this.validator = validator;
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
				log.error(e.getMessage());
			}
		}
	}

	private void createVoucher() {
		output.write(Message.VOUCHER_OPTION.getMessage());
		String chosenVoucher = input.read();
		validator.validateVoucherType(chosenVoucher);
		output.write(Message.DISCOUNT_OPTION.getMessage());
		String discountAmount = input.read();
		validator.validateDiscount(chosenVoucher, discountAmount);
		voucherService.createVoucher(chosenVoucher, Integer.parseInt(discountAmount));
	}

	public void writeAllVoucher() {
		List<Voucher> vouchers = voucherService.getAllVoucher();
		vouchers.stream()
			.forEach(voucher -> output.write(voucher.toString()));
	}

	public void writeAllBlacklist() {
		List<Customer> customers = customerService.getBlackList();
		customers.stream()
			.forEach(blacklist -> output.write(blacklist.toString()));
	}
}
