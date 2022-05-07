package com.prgrms.vouchermanagement;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.prgrms.vouchermanagement.commons.ErrorMessage;
import com.prgrms.vouchermanagement.commons.exception.NoMappingOneException;
import com.prgrms.vouchermanagement.io.InputView;
import com.prgrms.vouchermanagement.io.OutputView;
import com.prgrms.vouchermanagement.voucher.VoucherService;
import com.prgrms.vouchermanagement.voucher.VoucherType;
import com.prgrms.vouchermanagement.voucher.dto.VoucherInfo;

@Component
public class CommandLineApplication {

	private final InputView inputView;
	private final OutputView outputView;
	private final VoucherService voucherService;
	private final Logger logger = LoggerFactory.getLogger(CommandLineApplication.class);

	public CommandLineApplication(InputView inputView, OutputView outputView,
		VoucherService voucherService) {
		this.inputView = inputView;
		this.outputView = outputView;
		this.voucherService = voucherService;
	}

	public void run() {
		boolean onRunning = true;

		while (onRunning) {
			try {
				outputView.showMenu();

				MenuSelection selectedMenu = MenuSelection.from(
					inputView.inputSelectedMenu()
				);

				switch (selectedMenu) {
					case EXIT:
						onRunning = false;
						break;
					case CREATE:
						createVoucher();
						break;
					case LIST:
						showAllVouchers();
						break;
				}
			} catch (NoMappingOneException e) {
				logger.info("INPUT {} --> {}", e.getInput(), e.getMessage(), e);
			} catch (Exception e) {
				logger.error("예기치 못한 오류로 프로그램을 종료합니다", e);

				return;
			}
		}

		logger.info("EXIT PROGRAM");
	}

	private void showAllVouchers() {
		outputView.showAll(voucherService
			.getAll());
	}

	private void createVoucher() {
		VoucherType voucherType = selectVoucherType();

		long voucherDiscountInfo = getVoucherInfo();

		outputView.printVoucher(VoucherInfo.fromEntity(
			voucherService.create(voucherType, voucherDiscountInfo)
		));
	}

	private long getVoucherInfo() {
		while (true) {
			outputView.requestVoucherInfo();
			try {
				return inputView.inputDetailsInfo();
			} catch (IllegalArgumentException e) {
				logger.error("{}", e.getMessage(), e);
			}
		}
	}

	private VoucherType selectVoucherType() {

		while (true) {
			outputView.showVoucherMenu();
			try {
				String selectedVoucherType = inputView.inputVoucherType();

				return VoucherType.from(selectedVoucherType);
			} catch (NoMappingOneException e) {
				logger.info("INPUT {} --> {}", e.getInput(), e.getMessage(), e);
			}
		}
	}

	private enum MenuSelection {
		EXIT,
		CREATE,
		LIST;

		public static MenuSelection from(String type) {
			return Arrays.stream(MenuSelection.values())
				.filter(constant ->
					constant.name().equalsIgnoreCase(type))
				.findFirst()
				.orElseThrow(() ->
					new NoMappingOneException(type));
		}
	}
}



