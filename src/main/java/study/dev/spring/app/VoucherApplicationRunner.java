package study.dev.spring.app;

import lombok.RequiredArgsConstructor;
import study.dev.spring.app.exception.ExitException;
import study.dev.spring.common.io.InputHandler;
import study.dev.spring.common.io.OutputHandler;
import study.dev.spring.voucher.presentation.VoucherController;

@RequiredArgsConstructor
public class VoucherApplicationRunner {

	private static final String EXIT = "EXIT";

	private final VoucherController voucherController;
	private final InputHandler inputHandler;
	private final OutputHandler outputHandler;

	public void run() {
		outputHandler.showStartMessage();
		outputHandler.showSystemMessage("사용할 기능을 입력해주세요 : ");
		String function = inputHandler.inputString();

		if (function.equalsIgnoreCase(EXIT)) {
			throw new ExitException();
		}

		VoucherMethodExecutor executor = VoucherMethodExecutor.convert(function);
		executor.execute(voucherController);
	}
}
