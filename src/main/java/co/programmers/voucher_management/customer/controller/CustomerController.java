package co.programmers.voucher_management.customer.controller;

import java.text.MessageFormat;

import org.springframework.stereotype.Controller;

import co.programmers.voucher_management.customer.entity.Customer;
import co.programmers.voucher_management.customer.service.CustomerService;
import co.programmers.voucher_management.exception.InvalidDataException;
import co.programmers.voucher_management.view.InputView;
import co.programmers.voucher_management.view.OutputView;

@Controller
public class CustomerController {
	private final CustomerService customerService;
	private final OutputView outputView;
	private final InputView<String> inputView;

	public CustomerController(CustomerService customerService, OutputView outputView, InputView inputView) {
		this.customerService = customerService;
		this.outputView = outputView;
		this.inputView = inputView;
	}

	public void executeCustomerMenu(String commandNum) {
		switch (commandNum) {
			case "1":
				inquiryBlackList();
				break;
			case "2":
				inquiryCustomerByVoucher();
				break;
			default:
				throw new InvalidDataException("Unsupported command");
		}
	}

	public void inquiryBlackList() {
		String rating = Customer.Rating.BLACKLIST.toString();
		customerService.inquireByRating(rating);
	}

	public void inquiryCustomerByVoucher() {
		String requestMessageFormat = "Input {0} >> ";
		outputView.print(MessageFormat.format(requestMessageFormat, "ID of a voucher"));
		long voucherId = Long.parseLong(inputView.input());

		customerService.inquiryCustomerByVoucher(voucherId);
	}

}
