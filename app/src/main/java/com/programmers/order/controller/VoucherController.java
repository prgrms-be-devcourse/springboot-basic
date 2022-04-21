package com.programmers.order.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.programmers.order.domain.Voucher;
import com.programmers.order.exception.ServiceException;
import com.programmers.order.factory.VoucherManagerFactory;
import com.programmers.order.io.Input;
import com.programmers.order.io.Output;
import com.programmers.order.manager.VoucherManager;
import com.programmers.order.repository.VoucherRepository;
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
	private final VoucherRepository voucherRepository;

	public VoucherController(Input input, Output output, VoucherManagerFactory voucherManagerFactory,
			VoucherRepository voucherRepository) {
		this.input = input;
		this.output = output;
		this.voucherManagerFactory = voucherManagerFactory;
		this.voucherRepository = voucherRepository;
	}

	public void run() {
		MenuType menuType = MenuType.NONE;

		while (menuType.isReEnter()) {
			String menu = input.read(BasicMessage.INIT);
			menuType = MenuType.of(menu);

			switch (menuType) {
				case CREATE -> {
					createVoucher();
				}
				case LIST -> {
					showVouchers();
				}
				case NONE -> {
					output.write(ErrorMessage.CLIENT_ERROR);
					logger.error("error : {}", ErrorMessage.CLIENT_ERROR);
					continue;
				}
			}

			output.write(BasicMessage.NEW_LINE);
		}

		output.write(BasicMessage.EXIT);
	}

	// todo : 스코프 범위 줄이기(불필요한 부분 삭제)
	private void createVoucher() {
		VoucherType voucherType = VoucherType.NONE;

		do {
			String voucher = input.read(BasicMessage.VOUCHER_SELECT);
			voucherType = VoucherType.of(voucher);
		} while (voucherType.isReEnter());

		try {
			VoucherManager voucherManager = voucherManagerFactory.getVoucherManager(voucherType);
			voucherRepository.saveVoucher(voucherManager.create());
		} catch (ServiceException.NotSupportedException exception) {
			output.write(ErrorMessage.CLIENT_ERROR);
			logger.error("error : {}", ErrorMessage.CLIENT_ERROR);
		}

	}

	private void showVouchers() {
		output.write(BasicMessage.NEW_LINE);

		List<Voucher> vouchers = voucherRepository.getVouchers();

		if (vouchers.isEmpty()) {
			output.write(BasicMessage.NOT_EXIST_DATE);

			return;
		}

		String voucherBundle = vouchers
				.stream()
				.map(Voucher::show)
				.collect(Collectors.joining("\n"));
		output.write(voucherBundle);
	}

}
