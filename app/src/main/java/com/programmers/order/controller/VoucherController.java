package com.programmers.order.controller;

import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.programmers.order.domain.Voucher;
import com.programmers.order.factory.VoucherFactory;
import com.programmers.order.io.Input;
import com.programmers.order.io.Output;
import com.programmers.order.manager.VoucherManager;
import com.programmers.order.manager.store.StoreManager;
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
	private final StoreManager storeManager;

	public VoucherController(Input input, Output output, VoucherFactory voucherFactory, StoreManager storeManager) {
		this.input = input;
		this.output = output;
		this.voucherFactory = voucherFactory;
		this.storeManager = storeManager;
	}

	public void run() {
		while (true) {
			String menu = input.read(BasicMessage.INIT);
			MenuType menuType = MenuType.getMenuType(menu);

			switch (menuType) {
				case CREATE -> {
					createVoucher();
				}
				case LIST -> {
					showVouchers();
				}
				case EXIT -> {
					output.write(BasicMessage.EXIT);

					return;
				}
				case NONE -> {
					output.write(ErrorMessage.CLIENT_ERROR);
					logger.error("error : {}", ErrorMessage.CLIENT_ERROR);
					continue;
				}
			}

			output.write(BasicMessage.NEW_LINE);
		}

	}

	private void createVoucher() {
		while (true) {
			String voucher = input.read(BasicMessage.VOUCHER_SELECT);

			if (this.isNotValidVoucher(voucher)) {
				output.write(ErrorMessage.CLIENT_ERROR);
				logger.error("error : {}", ErrorMessage.CLIENT_ERROR);
				continue;
			}

			VoucherType voucherType = VoucherType.getVoucherType(voucher);
			VoucherManager voucherManager = voucherFactory.getVoucherManager(voucherType);

			storeManager.saveVoucher(voucherManager.create());
			break;
		}
	}

	private void showVouchers() {
		String vouchers = storeManager.getVouchers()
				.stream()
				.map(Voucher::show)
				.collect(Collectors.joining("\n"));

		output.write(vouchers);
	}

	private boolean isNotValidVoucher(String voucher) {
		return VoucherType.NONE.name().equals(voucher);
	}

}
