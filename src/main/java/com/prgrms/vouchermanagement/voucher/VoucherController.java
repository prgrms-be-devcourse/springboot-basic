package com.prgrms.vouchermanagement.voucher;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import com.prgrms.vouchermanagement.commons.CodeMappable;
import com.prgrms.vouchermanagement.commons.exception.NoMappingOneException;
import com.prgrms.vouchermanagement.voucher.io.VoucherConsole;

@Controller
public class VoucherController {

	private final VoucherConsole console;
	private final VoucherService voucherService;
	private final Logger logger = LoggerFactory.getLogger(VoucherController.class);

	public VoucherController(VoucherConsole console, VoucherService voucherService) {
		this.console = console;
		this.voucherService = voucherService;
	}

	public void run() {
		while (true) {
			try {
				MenuSelection selectedMenu = selectMenu();

				switch (selectedMenu) {
					case EXIT:
						return;
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
				logger.error("예기치 못한 오류 발생", e);

				return;
			}
		}
	}

	private void showAllVouchers() {
		console.showAll(voucherService
			.findAllVoucher());
	}

	private void createVoucher() {

		VoucherType voucherType = selectVoucherType();

		long voucherDiscountInfo = getVoucherInfo();

		voucherService.publishVoucher(voucherType, voucherDiscountInfo)
			.ifPresentOrElse(voucher ->
				console.showVoucherInfo(voucher), () -> console.failCreation()
			);

	}

	private long getVoucherInfo() {
		while (true) {
			try {
				console.requestVoucherInfo();

				return console.inputVoucherInfo();
			} catch (IllegalArgumentException e) {
				logger.error("{}", e.getMessage(), e);
			}
		}
	}

	private MenuSelection selectMenu() {
		console.showMenu();

		String menuSelected = console.selectedMenu();

		return MenuSelection.from(menuSelected);
	}

	private VoucherType selectVoucherType() {
		while (true) {
			try {
				console.showVoucherMenu();

				String selectedVoucherType = console.selectVoucherType();

				return VoucherType.from(selectedVoucherType);
			} catch (NoMappingOneException e) {
				logger.info("INPUT {} --> {}", e.getInput(), e.getMessage(), e);
			}
		}
	}

	private enum MenuSelection implements CodeMappable {
		EXIT("exit"),
		CREATE("create"),
		LIST("list");

		private final String code;

		MenuSelection(String code) {
			this.code = code;
		}

		public static MenuSelection from(String type) {
			return Arrays.stream(MenuSelection.values())
				.filter(constant ->
					constant.getMappingCode().equalsIgnoreCase(type))
				.findFirst()
				.orElseThrow(() ->
					new NoMappingOneException(type));
		}

		@Override
		public String getMappingCode() {
			return this.code;
		}
	}
}



