package co.programmers.voucher.controller;

import java.io.IOException;
import java.text.MessageFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import co.programmers.voucher.dto.Response;
import co.programmers.voucher.dto.VoucherCreationRequestDTO;
import co.programmers.voucher.service.CreationService;
import co.programmers.voucher.service.InquiryService;
import co.programmers.voucher.view.InputView;
import co.programmers.voucher.view.OutputView;

@Controller
public class VoucherCommandLineRunner implements VoucherApplicationRunner {
	private final CreationService creationService;
	private final InquiryService inquiryService;
	private final OutputView outputView;
	private final InputView inputView;

	@Autowired
	public VoucherCommandLineRunner(CreationService creationService, InquiryService inquiryService,
			OutputView outputView, InputView inputView) {
		this.creationService = creationService;
		this.inquiryService = inquiryService;
		this.outputView = outputView;
		this.inputView = inputView;
	}

	public void run() throws IOException {
		String menu;
		Response response;
		do {
			outputView.printGuideMessage();
			menu = (String)inputView.input();
			switch (menu.toLowerCase()) {
				case ("exit"):
				case ("x"):
					return;
				case ("create"):
				case ("c"):
					VoucherCreationRequestDTO voucherCreationRequestDTO = request();
					response = creationService.run(voucherCreationRequestDTO);
					break;
				case ("list"):
				case ("l"):
					response = inquiryService.run(new VoucherCreationRequestDTO());
					break;
				default:
					throw new IllegalArgumentException("* Invalid input for menu *");
			}
			outputView.print(response);
		} while (!"EXIT".equalsIgnoreCase(menu));
	}

	VoucherCreationRequestDTO request() throws IOException {
		String REQUEST_MESSAGE_FORMAT = "Input {0} >> ";

		outputView.print(MessageFormat.format(REQUEST_MESSAGE_FORMAT, "amount"));
		int amount = Integer.parseInt((String)inputView.input());

		outputView.print(MessageFormat.format(REQUEST_MESSAGE_FORMAT, "name"));
		String name = (String)inputView.input();

		outputView.print(MessageFormat.format(REQUEST_MESSAGE_FORMAT, "description"));
		String description = (String)inputView.input();

		outputView.print(MessageFormat.format(REQUEST_MESSAGE_FORMAT,
				"discount type. Fixed(for FixedAmountVoucher) or Percent(for PercentDiscountVoucher)"));
		String type = (String)inputView.input();

		return VoucherCreationRequestDTO.builder()
				.discountAmount(amount)
				.name(name)
				.description(description)
				.discountStrategy(type)
				.build();
	}
}
