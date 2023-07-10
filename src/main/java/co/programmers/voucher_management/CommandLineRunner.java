package co.programmers.voucher_management;

import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;

import co.programmers.voucher_management.common.MenuDescription;
import co.programmers.voucher_management.common.Response;
import co.programmers.voucher_management.customer.controller.CustomerController;
import co.programmers.voucher_management.exception.MenuTypeMismatchException;
import co.programmers.voucher_management.view.InputView;
import co.programmers.voucher_management.view.OutputView;
import co.programmers.voucher_management.voucher.controller.VoucherController;

@Profile("!test")
@Controller
public class CommandLineRunner implements org.springframework.boot.CommandLineRunner {
	private final OutputView outputView;
	private final InputView<String> inputView;
	VoucherController voucherController;
	CustomerController customerController;

	public CommandLineRunner(OutputView outputView, InputView<String> inputView,
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
		Response response;
		do {
			outputView.printGuideMessage();
			userInput = inputView.input();
			try {
				response = executeCommand(userInput);
			} catch (MenuTypeMismatchException menuTypeMismatchException) {
				response = new Response(Response.State.FAILED, "Unsupported menu");
			}
			outputView.print(response);
		} while (!(userInput.equals("x") || userInput.equals("0")));
	}

	private Response executeCommand(String userInput) {
		if (userInput.equals("0") || userInput.equals("x")) {
			return new Response<>(Response.State.SUCCESS, List.of("Terminated"));
		}
		String menuDescription = MenuDescription.of(userInput);
		outputView.print(menuDescription);
		String commandNum = inputView.input();
		switch (userInput) {
			case "1":
				return voucherController.executeVoucherMenu(commandNum);
			case "2":
				return customerController.executeCustomerMenu(commandNum);
			default:
				return new Response(Response.State.FAILED, "Unsupported menu");
		}
	}
}
