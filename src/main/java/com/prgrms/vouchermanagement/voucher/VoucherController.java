package com.prgrms.vouchermanagement.voucher;

import java.util.Arrays;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import com.prgrms.vouchermanagement.commons.CodeMappable;

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
				Optional<MenuSelection> selectedMenu = selectMenu();

				if (selectedMenu.isEmpty()) {
					console.notifyNoMappingSelection();
					continue;
				}

				switch (selectedMenu.get()) {
					case EXIT:
						return;
					case CREATE:
						createVoucher();
						break;
					case LIST:
						showAllVouchers();
						break;
				}
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
		Optional<VoucherType> voucherType = selectVoucherType();

		if (voucherType.isEmpty()) {
			console.notifyNoMappingSelection();

			return;
		}

		long voucherDiscountInfo = getVoucherInfo();

		voucherService.publishVoucher(voucherType.get(), voucherDiscountInfo)
			.ifPresentOrElse(voucher ->
				console.showVoucherInfo(voucher), () ->
				console.failCreation()
			);
	}

	private long getVoucherInfo(){
		while(true) {
			try {
				console.requestVoucherInfo();

				long voucherDiscountInfo = console.inputVoucherInfo();

				return voucherDiscountInfo;
			} catch (IllegalArgumentException e){
				logger.error("{}",e.getMessage(),e);
			}
		}
	}

	private Optional<MenuSelection> selectMenu() {
		console.showMenu();

		String menuSelected = console.selectedMenu();

		return findMappingOne(MenuSelection.class, menuSelected);
	}

	private Optional<VoucherType> selectVoucherType() {
		console.showVoucherMenu();

		String selectedVoucherType = console.selectVoucherType();

		return findMappingOne(VoucherType.class, selectedVoucherType);
	}

	private <T extends Enum<?> & CodeMappable> Optional<T> findMappingOne(Class<T> clazz, String target) {

		return Arrays.stream(clazz.getEnumConstants())
			.filter(type -> type.isMappedType(target))
			.findAny();
	}

	private enum MenuSelection implements CodeMappable {
		EXIT("exit"),
		CREATE("create"),
		LIST("list");

		private final String code;

		MenuSelection(String code) {
			this.code = code;
		}

		@Override
		public boolean isMappedType(String menu) {

			return code.equalsIgnoreCase(menu);
		}
	}
}



