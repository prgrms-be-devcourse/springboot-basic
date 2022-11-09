package org.prgrms.springorder.controller;

import org.prgrms.springorder.domain.Message;
import org.prgrms.springorder.domain.VoucherType;
import org.prgrms.springorder.exception.NoSuchCommandException;
import org.prgrms.springorder.io.Input;
import org.prgrms.springorder.io.Output;
import org.prgrms.springorder.repository.VoucherRepository;
import org.prgrms.springorder.service.CustomerService;
import org.prgrms.springorder.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class VoucherController implements Runnable{

	private final Logger logger = LoggerFactory.getLogger(VoucherController.class);
	private final ControllerStatus controllerStatus;
	private final Input input;
	private final Output output;
	private final VoucherService voucherService;
	private final CustomerService customerService;

	public VoucherController(ControllerStatus controllerStatus, Input input, Output output,
		VoucherService voucherService, CustomerService customerService) {
		this.controllerStatus = controllerStatus;
		this.input = input;
		this.output = output;
		this.voucherService = voucherService;
		this.customerService = customerService;
	}

	@Override
	public void run() {
		while (controllerStatus.isRunning()) {
			try {
				Command menu = Command.getOrder(input.read(Message.MENU_MESSAGE));
				execute(menu);
			} catch (RuntimeException e) {
				logger.error(e.getMessage());
				output.write(e.getMessage());
			}
		}
	}

	private void execute(Command menu) {
		switch (menu) {
			case CREATE -> {
				VoucherType voucherType = VoucherType.getVoucherByOrder(input.read(Message.SELECT_VOUCHER_MESSAGE));
				long value = Long.parseLong(input.read(voucherType.getMessage()));
				voucherService.createVoucher(voucherType, value);
			}
			case LIST -> output.writeList(voucherService.getList());
			case BLACK_LIST -> output.writeList(customerService.getBlackList());
			case EXIT -> controllerStatus.stop();
			default -> {
				throw new NoSuchCommandException("Wrong Command");
			}
		}
	}


}
