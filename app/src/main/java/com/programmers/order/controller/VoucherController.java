package com.programmers.order.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.programmers.order.domain.Voucher;
import com.programmers.order.dto.CustomerDto;
import com.programmers.order.exception.ServiceException;
import com.programmers.order.factory.VoucherManagerFactory;
import com.programmers.order.io.Input;
import com.programmers.order.io.Output;
import com.programmers.order.manager.VoucherManager;
import com.programmers.order.message.BasicMessage;
import com.programmers.order.message.ErrorMessage;
import com.programmers.order.service.VoucherService;
import com.programmers.order.type.DomainMenu.MenuType;
import com.programmers.order.type.ProgramType;
import com.programmers.order.type.VoucherType;

@Component("Voucher")
public class VoucherController implements Controller {
	private static final Logger logger = LoggerFactory.getLogger(VoucherController.class);
	private static final String EMPTY_STRING = "";
	private static final String LINE = "\n";

	private final Input input;
	private final Output output;
	private final VoucherManagerFactory voucherManagerFactory;
	private final VoucherService voucherService;

	public VoucherController(Input input, Output output, VoucherManagerFactory voucherManagerFactory,
			VoucherService voucherRepository) {
		this.input = input;
		this.output = output;
		this.voucherManagerFactory = voucherManagerFactory;
		this.voucherService = voucherRepository;
	}

	@Override
	public void run() {
		MenuType menuType = MenuType.NONE;

		while (menuType.isReEnter()) {
			String menu = input.read(BasicMessage.VOUCHER_INIT);
			menuType = MenuType.of(menu);

			switch (menuType) {
				case CREATE -> {
					createVoucher();
				}
				case LIST -> {
					showVouchers();
				}
				case LIST_UP_WITH_CUSTOMER -> {
					listUpWithCustomer();
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

	@Override
	public ProgramType getType() {
		return ProgramType.VOUCHER;
	}

	private void createVoucher() {
		VoucherType voucherType = VoucherType.NONE;

		do {
			String voucher = input.read(BasicMessage.VOUCHER_SELECT);
			voucherType = VoucherType.of(voucher);
		} while (voucherType.isReEnter());

		try {
			VoucherManager voucherManager = voucherManagerFactory.getVoucherManager(voucherType);
			voucherService.save(voucherManager.create());
		} catch (ServiceException.NotSupportedException exception) {
			output.write(ErrorMessage.CLIENT_ERROR);
			logger.error("error : {}", ErrorMessage.CLIENT_ERROR);
		}

	}

	private void showVouchers() {
		output.write(BasicMessage.NEW_LINE);

		List<Voucher> vouchers = voucherService.lookUp();

		if (vouchers.isEmpty()) {
			output.write(BasicMessage.NOT_EXIST_DATE);

			return;
		}

		String voucherBundle = vouchers
				.stream()
				.map(Voucher::show)
				.collect(Collectors.joining(LINE, LINE, LINE));

		output.write(voucherBundle);
	}

	private void listUpWithCustomer() {
		boolean isReEnter = true;
		String voucherId = EMPTY_STRING;

		do {
			voucherId = input.read(BasicMessage.VOUCHER_LIST_UP_WITH_CUSTOMER);
			isReEnter = voucherService.isNotExist(voucherId);
		} while (isReEnter);

		String value = voucherService.lookUpForCustomer(voucherId)
				.stream()
				.map(CustomerDto.ResponseDto::show)
				.collect(Collectors.joining(LINE, LINE, LINE));

		output.write(value);
	}

}
