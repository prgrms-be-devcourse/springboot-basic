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

	CREATE("create") {
		@Override
		public void doCommand(Input input, Output output, VoucherService voucherService,
			CustomerService customerService) {
			output.write(Message.VOUCHER_OPTION.getMessage());
			VoucherType choseVoucherType = VoucherType.getVoucherType(input.read());
			output.write(Message.DISCOUNT_OPTION.getMessage());
			String discountAmount = input.read();
			voucherService.createVoucher(discountAmount, choseVoucherType);
		}
	},
	LIST("list") {
		@Override
		public void doCommand(Input input, Output output, VoucherService voucherService,
			CustomerService customerService) {
			List<Voucher> vouchers = voucherService.getAllVoucher();
			vouchers.forEach(voucher -> output.write(voucher.toString()));
		}
	},
	BLACKLIST("blacklist") {
		@Override
		public void doCommand(Input input, Output output, VoucherService voucherService,
			CustomerService customerService) {
			List<Customer> customers = customerService.getBlackList();
			customers.forEach(blacklist -> output.write(blacklist.toString()));
		}
	},
	EXIT("exit") {
		@Override
		public void doCommand(Input input, Output output, VoucherService voucherService,
			CustomerService customerService) {
			ControllerPower.stop();
		}
	};

	private static final Logger log = LoggerFactory.getLogger(Command.class);
	private final String option;

	Command(String option) {
		this.option = option;
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

	public abstract void doCommand(Input input, Output output, VoucherService voucherService,
		CustomerService customerService);
}
