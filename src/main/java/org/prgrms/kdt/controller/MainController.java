package org.prgrms.kdt.controller;

import org.prgrms.kdt.enums.Command;
import org.prgrms.kdt.enums.VoucherType;
import org.prgrms.kdt.view.InputView;
import org.prgrms.kdt.view.OutputView;

public class MainController {

	private InputView inputView;

	private OutputView outputView;

	public MainController(InputView inputView, OutputView outputView) {
		this.inputView = inputView;
		this.outputView = outputView;
	}

	public void startControl() {
		Command command = null;

		do {
			outputView.displayCommandGuideMessage();
			command = inputView.getCommand();
			executeCommand(command);
		} while(command != Command.EXIT);
	}

	private void executeCommand(Command command) {
		switch (command) {
			case CREATE -> {
				outputView.displayCreateVoucherMessage();
				VoucherType voucherType = inputView.getVoucherType();
				int amount = inputView.getAmount();
			}

			case LIST -> {
				outputView.displayVoucherList();
			}

			case EXIT -> {
				outputView.displayExitMessage();
			}
		}
	}

}
