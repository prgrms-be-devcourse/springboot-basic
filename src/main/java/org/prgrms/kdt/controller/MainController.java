package org.prgrms.kdt.controller;

import java.util.List;

import org.prgrms.kdt.controller.service.VoucherService;
import org.prgrms.kdt.enums.Command;
import org.prgrms.kdt.enums.VoucherType;
import org.prgrms.kdt.model.dto.VoucherDTO;
import org.prgrms.kdt.util.VoucherFactory;
import org.prgrms.kdt.view.InputView;
import org.prgrms.kdt.view.OutputView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MainController {

	private InputView inputView;

	private OutputView outputView;

	private VoucherService voucherService;

	@Autowired
	public MainController(InputView inputView,
		OutputView outputView,
		VoucherService voucherService
	) {
		this.inputView = inputView;
		this.outputView = outputView;
		this.voucherService = voucherService;
	}

	public void startControl() {
		Command command = null;

		do {
			outputView.displayCommandGuideMessage();
			command = inputView.getCommand();
			executeCommand(command);
		} while (command != Command.EXIT);
	}

	private void executeCommand(Command command) {
		switch (command) {
			case CREATE -> {
				outputView.displayCreateVoucherMessage();
				VoucherType voucherType = inputView.getVoucherType();
				int amount = inputView.getAmount();

				VoucherDTO voucherDTO = VoucherFactory.getVoucherDTO(amount, voucherType);
				this.voucherService.createVoucher(voucherDTO);
			}
			case LIST -> {
				List<VoucherDTO> voucherDTOS = voucherService.getVouchers();
				outputView.displayVoucherList(voucherDTOS);
			}
			case EXIT -> {
				outputView.displayExitMessage();
			}
		}
	}

}
