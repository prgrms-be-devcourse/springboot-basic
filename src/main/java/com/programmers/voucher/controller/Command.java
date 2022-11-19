package com.programmers.voucher.controller;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.programmers.voucher.domain.customer.model.Customer;
import com.programmers.voucher.domain.customer.service.CustomerService;
import com.programmers.voucher.domain.voucher.model.Voucher;
import com.programmers.voucher.domain.voucher.model.VoucherType;
import com.programmers.voucher.domain.voucher.service.VoucherService;
import com.programmers.voucher.exception.ExceptionMessage;
import com.programmers.voucher.io.Input;
import com.programmers.voucher.io.Output;

public enum Command {

	CREATE("create", (Input input, Output output, VoucherService voucherService, CustomerService customerService) -> {
		output.write(Message.VOUCHER_OPTION.getMessage());
		VoucherType chosenVoucherType = VoucherType.getVoucherType(input.read());
		output.write(Message.DISCOUNT_OPTION.getMessage());
		String discount = input.read();
		voucherService.createVoucher(chosenVoucherType, discount);
	}),
	LIST("list", (Input input, Output output, VoucherService voucherService, CustomerService customerService) -> {
		List<Voucher> vouchers = voucherService.getAllVoucher();
		vouchers.forEach(voucher -> output.write(voucher.toString()));
	}),
	BLACKLIST("blacklist",
		(Input input, Output output, VoucherService voucherService, CustomerService customerService) -> {
			List<Customer> customers = customerService.getBlackList();
			customers.forEach(customer -> output.write(customer.toString()));
		}),
	EXIT("exit", (Input input, Output output, VoucherService voucherService, CustomerService customerService) -> {
		ControllerPower.stop();
	});

	private static final Logger log = LoggerFactory.getLogger(Command.class);
	private final String option;
	private final QuadFunction<Input, Output, VoucherService, CustomerService> commandFunction;

	Command(String option,
		QuadFunction<Input, Output, VoucherService, CustomerService> commandFunction) {
		this.option = option;
		this.commandFunction = commandFunction;
	}

	public static Command getCommand(String chosenCommand) {
		return Arrays.stream(Command.values())
			.filter(command -> command.option.equals(chosenCommand))
			.findFirst()
			.orElseThrow(() -> {
				log.error(ExceptionMessage.WRONG_COMMAND.getMessage());
				throw new IllegalArgumentException(ExceptionMessage.WRONG_COMMAND.getMessage());
			});
	}

	public void doCommand(Input input, Output output, VoucherService voucherService, CustomerService customerService) {
		commandFunction.apply(input, output, voucherService, customerService);
	}
}
