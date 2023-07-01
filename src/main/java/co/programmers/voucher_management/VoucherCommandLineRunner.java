package co.programmers.voucher_management;

import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import co.programmers.voucher_management.common.Response;
import co.programmers.voucher_management.customer.service.CustomerInquiryService;
import co.programmers.voucher_management.exception.InvalidUserInputException;
import co.programmers.voucher_management.view.InputView;
import co.programmers.voucher_management.view.OutputView;
import co.programmers.voucher_management.voucher.dto.VoucherRequestDTO;
import co.programmers.voucher_management.voucher.service.VoucherCreationService;
import co.programmers.voucher_management.voucher.service.VoucherInquiryService;

@Controller
public class VoucherCommandLineRunner implements CommandLineRunner {
	private static final Logger logger = LoggerFactory.getLogger(VoucherCommandLineRunner.class);
	private final VoucherCreationService voucherCreationService;
	private final VoucherInquiryService voucherInquiryService;
	private final CustomerInquiryService customerInquiryService;

	private final OutputView outputView;
	private final InputView<String> inputView;

	public VoucherCommandLineRunner(VoucherCreationService voucherCreationService,
			VoucherInquiryService voucherInquiryService,
			CustomerInquiryService customerInquiryService, OutputView outputView, InputView<String> inputView) {
		this.voucherCreationService = voucherCreationService;
		this.voucherInquiryService = voucherInquiryService;
		this.customerInquiryService = customerInquiryService;
		this.outputView = outputView;
		this.inputView = inputView;
	}

	@Override
	public void run(String... args) {
		String menu;
		Response response;
		while (true) {
			outputView.printGuideMessage();
			menu = inputView.input();
			logger.info("Input : {}", menu);
			switch (menu.toLowerCase()) {
				case "exit":
				case "x":
					return;
				case "create":
				case "c":
					response = createVoucher();
					break;
				case "list":
				case "l":
					response = voucherInquiryService.run();
					break;
				case "blacklist":
				case "b":
					response = customerInquiryService.inquireBlackList();
					break;
				default:
					response = new Response<>(Response.State.FAILED, "* Invalid input for menu *");
					break;
			}
			outputView.print(response);
		}
	}

	private Response<String> createVoucher() {
		try {
			VoucherRequestDTO voucherRequestDTO = request();
			return voucherCreationService.run(voucherRequestDTO);
		} catch (InvalidUserInputException invalidUserInputException) {
			return new Response<>(Response.State.FAILED, invalidUserInputException.getMessage());
		}
	}

	VoucherRequestDTO request() {
		String requestMessageFormat = "Input {0} >> ";

		outputView.print(MessageFormat.format(requestMessageFormat, "amount"));
		int amount = Integer.parseInt(inputView.input());

		outputView.print(MessageFormat.format(requestMessageFormat,
				"discount type. Fixed(for FixedAmountVoucher) or Percent(for PercentDiscountVoucher)"));
		String type = inputView.input();

		return VoucherRequestDTO.builder()
				.discountAmount(amount)
				.discountStrategy(type)
				.build();
	}

}
