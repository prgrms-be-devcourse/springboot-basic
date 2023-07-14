package co.programmers.voucher_management;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;

import co.programmers.voucher_management.common.MenuDescription;
import co.programmers.voucher_management.customer.controller.CustomerController;
import co.programmers.voucher_management.exception.InvalidDataException;
import co.programmers.voucher_management.view.InputView;
import co.programmers.voucher_management.view.OutputView;
import co.programmers.voucher_management.voucher.controller.VoucherController;

@Profile("!test")
@Controller
public class CommandLineApplication implements CommandLineRunner {
	private final OutputView outputView;
	private final InputView<String> inputView;
	VoucherController voucherController;
	CustomerController customerController;

	public CommandLineApplication(OutputView outputView, InputView<String> inputView,
			VoucherController voucherController,
			CustomerController customerController) {
		this.outputView = outputView;
		this.inputView = inputView;
		this.voucherController = voucherController;
		this.customerController = customerController;
	}

	@Override
	public void run(String... args) {
		String userInput;
		do {
			outputView.printGuideMessage();
			userInput = inputView.input();
			try {
				executeCommand(userInput);
			} catch (RuntimeException runtimeException) {
				outputView.print(runtimeException.getMessage());
			}
		} while (!(userInput.equals("x") || userInput.equals("0")));
	}

	private void executeCommand(String userInput) {
		if (userInput.equals("0") || userInput.equals("x")) {
			return;
		}
		String menuDescription = MenuDescription.of(userInput);
		outputView.print(menuDescription);
		String commandNum = inputView.input();
		switch (userInput) {
			case "1":
				voucherController.executeVoucherMenu(commandNum);
				break;
			case "2":
				customerController.executeCustomerMenu(commandNum);
				break;
			default:
				throw new InvalidDataException("Unsupported menu");
		}
	}
}
