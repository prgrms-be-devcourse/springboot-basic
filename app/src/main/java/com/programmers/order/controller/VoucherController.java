package com.programmers.order.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.programmers.order.factory.VoucherFactory;
import com.programmers.order.io.Input;
import com.programmers.order.io.Output;
import com.programmers.order.manager.VoucherManager;
import com.programmers.order.message.BasicMessage;
import com.programmers.order.message.ErrorMessage;
import com.programmers.order.type.MenuType;
import com.programmers.order.type.VoucherType;

@Component
public class VoucherController {
	private static final Logger logger = LoggerFactory.getLogger(VoucherController.class);

	private final Input input;
	private final Output output;
	private final VoucherFactory voucherFactory;

	public VoucherController(Input input, Output output, VoucherFactory voucherFactory) {
		this.input = input;
		this.output = output;
		this.voucherFactory = voucherFactory;
	}

	public void run() {
		while (true) {
			String menu = input.read(BasicMessage.INIT);
			MenuType menuType = MenuType.getMenuType(menu);

			switch (menuType) {
				case CREATE -> {
					while (doSomething()){

					}
				}
				case LIST -> {

				}
				case EXIT -> {
					output.write(BasicMessage.EXIT);

					return;
				}
				case NONE -> {
					output.write(ErrorMessage.CLIENT_ERROR);
					continue;
				}
			}

			output.write(BasicMessage.NEW_LINE);
		}

	}

	// todo : naming 고민중 !!
	private boolean doSomething() {
		String voucher = input.read(BasicMessage.VOUCHER_SELECT);

		if (isNotValidVoucher(voucher)) {
			output.write(ErrorMessage.CLIENT_ERROR);
			return false;
		}

		VoucherType voucherType = VoucherType.getVoucherType(voucher);
		VoucherManager voucherManager = voucherFactory.getVoucherManager(voucherType);

		voucherManager.create();

		return true;
	}

	private boolean isNotValidVoucher(String voucher) {
		return VoucherType.NONE.name().equals(voucher);
	}

}
