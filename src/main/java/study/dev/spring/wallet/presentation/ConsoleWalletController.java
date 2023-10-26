package study.dev.spring.wallet.presentation;

import static study.dev.spring.wallet.presentation.constants.Message.*;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import study.dev.spring.common.io.InputHandler;
import study.dev.spring.common.io.OutputHandler;
import study.dev.spring.wallet.application.WalletService;

@Component
@RequiredArgsConstructor
public class ConsoleWalletController {

	private final WalletService walletService;
	private final InputHandler inputHandler;
	private final OutputHandler outputHandler;

	public void addWallet() {
		outputHandler.showSystemMessage(INPUT_CUSTOMER_ID.getValue());
		String customerId = inputHandler.inputString();
		outputHandler.showSystemMessage(INPUT_VOUCHER_ID.getValue());
		String voucherId = inputHandler.inputString();

		walletService.addWallet(customerId, voucherId);

		outputHandler.showSystemMessage(COMPLETE_ADD.getValue());
	}

	public void removeWalletByCustomer() {
		outputHandler.showSystemMessage(INPUT_CUSTOMER_ID.getValue());
		String customerId = inputHandler.inputString();

		walletService.deleteByCustomer(customerId);

		outputHandler.showSystemMessage(COMPLETE_ADD.getValue());
	}
}
