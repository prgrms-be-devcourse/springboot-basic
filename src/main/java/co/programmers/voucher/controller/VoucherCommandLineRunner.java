package co.programmers.voucher.controller;

import java.io.IOException;
import java.text.MessageFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import co.programmers.voucher.dto.Response;
import co.programmers.voucher.dto.VoucherCreationRequestDTO;
import co.programmers.voucher.service.CreationService;
import co.programmers.voucher.service.InquiryService;
import co.programmers.voucher.view.InputView;
import co.programmers.voucher.view.OutputView;

@Controller
public class VoucherCommandLineRunner implements CommandLineRunner {
	private final CreationService creationService;
	private final InquiryService inquiryService;
	private final OutputView outputView;
	private final InputView<String> inputView;

	@Autowired
	public VoucherCommandLineRunner(CreationService creationService, InquiryService inquiryService,
			OutputView outputView, InputView<String> inputView) {
		this.creationService = creationService;
		this.inquiryService = inquiryService;
		this.outputView = outputView;
		this.inputView = inputView;
	}

	@Override
	public void run(String... args) throws Exception {
		String menu;
		Response response;
		do {
			outputView.printGuideMessage();
			menu = inputView.input();
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
		String requestMessageFormat = "Input {0} >> ";

		outputView.print(MessageFormat.format(requestMessageFormat, "amount"));
		int amount = Integer.parseInt(inputView.input());

		outputView.print(MessageFormat.format(requestMessageFormat, "name"));
		String name = inputView.input();

		outputView.print(MessageFormat.format(requestMessageFormat, "description"));
		String description = inputView.input();

		outputView.print(MessageFormat.format(requestMessageFormat,
				"discount type. Fixed(for FixedAmountVoucher) or Percent(for PercentDiscountVoucher)"));
		String type = inputView.input();

		return VoucherCreationRequestDTO.builder()
				.discountAmount(amount)
				.name(name)
				.description(description)
				.discountStrategy(type)
				.build();
	}

}
