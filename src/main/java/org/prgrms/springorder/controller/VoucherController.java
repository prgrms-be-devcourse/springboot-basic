package org.prgrms.springorder.controller;

import org.prgrms.springorder.domain.Message;
import org.prgrms.springorder.exception.NoSuchCommandException;
import org.prgrms.springorder.io.Input;
import org.prgrms.springorder.io.Output;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VoucherController implements Runnable{

	private final Logger logger = LoggerFactory.getLogger(VoucherController.class);
	private final ControllerStatus controllerStatus;
	private final Input input;
	private final Output output;

	public VoucherController(ControllerStatus controllerStatus, Input input, Output output) {
		this.controllerStatus = controllerStatus;
		this.input = input;
		this.output = output;
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
		//Todo 서비스 구현하기
		switch (menu) {
			case CREATE ->
			case LIST ->
			case BLACK_LIST ->
			case EXIT ->
			default -> {
				throw new NoSuchCommandException("Wrong Command");
			}
		}
	}


}
