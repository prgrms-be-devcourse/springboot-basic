package com.programmers.order.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.programmers.order.domain.Voucher;
import com.programmers.order.exception.NotSupportedException;
import com.programmers.order.factory.VoucherManagerFactory;
import com.programmers.order.io.Input;
import com.programmers.order.io.Output;
import com.programmers.order.manager.VoucherManager;
import com.programmers.order.manager.store.VoucherStoreManager;
import com.programmers.order.message.BasicMessage;
import com.programmers.order.message.ErrorMessage;
import com.programmers.order.type.MenuType;
import com.programmers.order.type.VoucherType;

@Component
public class VoucherController {
	private static final Logger logger = LoggerFactory.getLogger(VoucherController.class);

	private final Input input;
	private final Output output;
	private final VoucherManagerFactory voucherManagerFactory;
	private final VoucherStoreManager voucherStoreManager;

	public VoucherController(Input input, Output output, VoucherManagerFactory voucherManagerFactory,
			VoucherStoreManager voucherStoreManager) {
		this.input = input;
		this.output = output;
		this.voucherManagerFactory = voucherManagerFactory;
		this.voucherStoreManager = voucherStoreManager;
	}

	public void run() {
		while (true) {
			String menu = input.read(BasicMessage.INIT);
			MenuType menuType = MenuType.of(menu);

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

			try {
				VoucherType voucherType = VoucherType.of(voucher);
				VoucherManager voucherManager = voucherManagerFactory.getVoucherManager(voucherType);

				voucherStoreManager.saveVoucher(voucherManager.create());

				break;
			} catch (NotSupportedException exception) {
				output.write(ErrorMessage.CLIENT_ERROR);
				logger.error("error : {}", ErrorMessage.CLIENT_ERROR);
			}
		}
	}

	private void showVouchers() {
		output.write(BasicMessage.NEW_LINE);

		List<Voucher> vouchers1 = voucherStoreManager.getVouchers();

		if (vouchers1.isEmpty()) {
			output.write(BasicMessage.NOT_EXIST_DATE);

			return;
		}

		String vouchers = vouchers1
				.stream()
				.map(Voucher::show)
				.collect(Collectors.joining("\n"));
		output.write(vouchers);
	}

}
