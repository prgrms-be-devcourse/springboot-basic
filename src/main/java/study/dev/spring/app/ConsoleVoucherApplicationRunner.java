package study.dev.spring.app;

import lombok.RequiredArgsConstructor;
import study.dev.spring.app.exception.ExitException;
import study.dev.spring.common.io.InputHandler;
import study.dev.spring.common.io.OutputHandler;
import study.dev.spring.customer.presentation.ConsoleCustomerController;
import study.dev.spring.voucher.presentation.ConsoleVoucherController;

@RequiredArgsConstructor
public class ConsoleVoucherApplicationRunner {

	private static final String EXIT = "EXIT";
	private static final String BLACK_LIST = "BLACK_LIST";

	private final ConsoleVoucherController consoleVoucherController;
	private final ConsoleCustomerController consoleCustomerController;
	private final InputHandler inputHandler;
	private final OutputHandler outputHandler;

	public void run() {
		outputHandler.showStartMessage();
		outputHandler.showSystemMessage("사용할 기능을 입력해주세요 : ");
		String function = inputHandler.inputString();

		if (function.equalsIgnoreCase(EXIT)) {
			throw new ExitException();
		}

		if (function.equalsIgnoreCase(BLACK_LIST)) {
			consoleCustomerController.findAllBlackListCustomers();
			return;
		}

		VoucherMethodExecutor executor = VoucherMethodExecutor.convert(function);
		executor.execute(consoleVoucherController);
	}
}
