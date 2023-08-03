package org.prgrms.kdt.controller;

import java.util.List;

import org.prgrms.kdt.enums.Command;
import org.prgrms.kdt.enums.VoucherType;
import org.prgrms.kdt.model.dto.VoucherDTO;
import org.prgrms.kdt.service.VoucherService;
import org.prgrms.kdt.util.VoucherFactory;
import org.prgrms.kdt.view.InputView;
import org.prgrms.kdt.view.OutputView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MainController {

	private static final Logger logger = LoggerFactory.getLogger(MainController.class);
	private final InputView inputView;
	private final OutputView outputView;
	private final VoucherService voucherService;

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
			try {
				command = inputView.getCommand();
				executeCommand(command);
			} catch (IllegalArgumentException e) {
				logger.error("잘못된 Command 입력");
				outputView.displayCommandErrorMessage();
			}
		} while (command != Command.EXIT);
	}

	private void executeCommand(Command command) {
		switch (command) {
			case CREATE -> {
				logger.info("바우처 생성 시도");
				outputView.displayCreateVoucherMessage();
				VoucherType voucherType = inputView.getVoucherType();
				int amount = inputView.getAmount();

				try {
					VoucherDTO voucherDTO = VoucherFactory.getVoucherDTO(amount, voucherType);
					this.voucherService.createVoucher(voucherDTO);
					logger.error("바우처 생성 성공");
				} catch (IllegalArgumentException e) {
					logger.error("잘못 된 할인 값으로 인한 바우처 생성 실패");
					outputView.displayAmountErrorMessage();
					executeCommand(Command.CREATE);
				}
			}
			case LIST -> {
				List<VoucherDTO> voucherDTOs = voucherService.getVouchers();
				outputView.displayVoucherList(voucherDTOs);
				logger.info("바우처 리스트 출력");
			}
			case EXIT -> {
				outputView.displayExitMessage();
				logger.info("바우처 메인 컨트롤러 종료");
			}
		}
	}

}
