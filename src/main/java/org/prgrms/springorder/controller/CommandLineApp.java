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
	private final VoucherService voucherService;
	private final CustomerService customerService;

	public CommandLineApp(IO io, VoucherService voucherService, CustomerService customerService) {
		this.io = io;
		this.voucherService = voucherService;
		this.customerService = customerService;
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
			case CREATE -> {
				VoucherType voucherType = VoucherType.getVoucherByOrder(io.read(SELECT_VOUCHER_MESSAGE));
				double value = Double.parseDouble(io.read(voucherType.getMessage()));
				voucherService.createVoucher(voucherType, value);
			}
			case LIST -> io.writeList(voucherService.getList());
			case BLACK_LIST -> io.writeList(customerService.getBlackList());
			case EXIT -> controllerStatus.stop();
			default -> {
				throw new NoSuchCommandException(ErrorMessage.No_SUCH_COMMAND_MESSAGE);
			}
		}
	}

}
