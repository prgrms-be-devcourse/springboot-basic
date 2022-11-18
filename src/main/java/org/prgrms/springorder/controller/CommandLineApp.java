package org.prgrms.springorder.controller;

import static org.prgrms.springorder.domain.Message.*;

import org.prgrms.springorder.domain.ErrorMessage;
import org.prgrms.springorder.domain.voucher.VoucherType;
import org.prgrms.springorder.exception.NoSuchCommandException;
import org.prgrms.springorder.io.IO;
import org.prgrms.springorder.service.CustomerService;
import org.prgrms.springorder.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CommandLineApp {

	private final Logger logger = LoggerFactory.getLogger(CommandLineApp.class);
	private final IO io;
	private final VoucherController voucherController;
	private final CustomerController customerController;

	public CommandLineApp(IO io, VoucherController voucherController,
		CustomerController customerController) {
		this.io = io;
		this.voucherController = voucherController;
		this.customerController = customerController;
	}

	public void run() {
		ControllerStatus controllerStatus = new ControllerStatus();
		while (controllerStatus.isRunning()) {
			try {
				Command menu = Command.getOrder(io.read(MENU_MESSAGE));
				execute(menu, controllerStatus);
			} catch (RuntimeException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
				io.write(e.getMessage());
			}
		}
	}

	private void execute(Command menu, ControllerStatus controllerStatus) {
		switch (menu) {
			case CREATE -> voucherController.createVoucher();
			case LIST -> voucherController.showVoucherList();
			case BLACK_LIST -> customerController.showBlackList();
			case EXIT -> controllerStatus.stop();
			default -> {
				throw new NoSuchCommandException(ErrorMessage.No_SUCH_COMMAND_MESSAGE);
			}
		}
	}

}
