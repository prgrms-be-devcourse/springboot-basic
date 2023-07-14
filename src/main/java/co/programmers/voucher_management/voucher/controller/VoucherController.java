package co.programmers.voucher_management.voucher.controller;

import java.text.MessageFormat;
import java.util.List;

import org.springframework.stereotype.Controller;

import co.programmers.voucher_management.customer.service.CustomerService;
import co.programmers.voucher_management.exception.InvalidDataException;
import co.programmers.voucher_management.view.InputView;
import co.programmers.voucher_management.view.OutputView;
import co.programmers.voucher_management.voucher.dto.VoucherAssignDTO;
import co.programmers.voucher_management.voucher.dto.VoucherRequestDTO;
import co.programmers.voucher_management.voucher.dto.VoucherResponseDTO;
import co.programmers.voucher_management.voucher.dto.VoucherUpdateDTO;
import co.programmers.voucher_management.voucher.service.VoucherService;

@Controller
public class VoucherController {
	private final String requestMessageFormat = "Input {0} >> ";
	private final VoucherService voucherService;
	private final CustomerService customerService;
	private final OutputView outputView;
	private final InputView<String> inputView;

	public VoucherController(VoucherService voucherService, CustomerService customerService, OutputView outputView,
			InputView<String> inputView) {
		this.voucherService = voucherService;
		this.customerService = customerService;
		this.outputView = outputView;
		this.inputView = inputView;
	}

	public void executeVoucherMenu(String commandNum) {
		switch (commandNum) {
			case "1":
				createVoucher();
				break;
			case "2":
				inquiryVoucherOf();
				break;
			case "3":
				updateVoucher();
				break;
			case "4":
				deleteVoucher();
				break;
			case "5":
				assignVoucher();
				break;
			case "6":
				listVoucherOfCustomer();
				break;
			case "7":
				deleteVoucherOfCustomer();
				break;
			default:
				throw new InvalidDataException("Unsupported Menu");
		}
	}

	//InvalidDataException | NumberFormatException |NoSuchDataException |EmptyAssignerException|RuntimeException
	public void createVoucher() {
		VoucherRequestDTO voucherRequestDTO = requestVoucherCreationData();
		VoucherResponseDTO voucherResponseDTO = voucherService.create(voucherRequestDTO);
		outputView.print(voucherResponseDTO);
	}

	private VoucherRequestDTO requestVoucherCreationData() {
		outputView.print(MessageFormat.format(requestMessageFormat, "amount"));
		int amount = Integer.parseInt(inputView.input());

		outputView.print(MessageFormat.format(requestMessageFormat,
				"discount type. Fixed(for FixedAmountVoucher) or Percent(for PercentDiscountVoucher)"));
		String type = inputView.input();

		return VoucherRequestDTO.builder()
				.discountAmount(amount)
				.discountType(type)
				.build();
	}

	public void inquiryVoucherOf() {
		List<VoucherResponseDTO> voucherResponseDTOS = voucherService.inquiryVoucherOf();
		outputView.print(voucherResponseDTOS);
	}

	public void updateVoucher() {
		VoucherUpdateDTO voucherUpdateDTO = requestVoucherUpdateData();
		VoucherResponseDTO voucherResponseDTO = voucherService.update(voucherUpdateDTO);
		outputView.print(voucherResponseDTO);
	}

	private VoucherUpdateDTO requestVoucherUpdateData() {
		outputView.print(MessageFormat.format(requestMessageFormat, "id"));
		long id = Long.parseLong(inputView.input());

		outputView.print(MessageFormat.format(requestMessageFormat, "amount"));
		int amount = Integer.parseInt(inputView.input());

		outputView.print(MessageFormat.format(requestMessageFormat,
				"discount type. Fixed(for FixedAmountVoucher) or Percent(for PercentDiscountVoucher)"));
		String type = inputView.input();

		return VoucherUpdateDTO.builder()
				.id(id)
				.discountAmount(amount)
				.discountType(type)
				.build();
	}

	public void deleteVoucher() {
		outputView.print(MessageFormat.format(requestMessageFormat, "ID of a voucher"));
		Long id = Long.parseLong(inputView.input());
		voucherService.deleteById(id);
	}

	public void assignVoucher() {
		VoucherAssignDTO voucherAssignDTO = requestVoucherAssignData();
		VoucherResponseDTO voucherResponseDTO = voucherService.assignVoucher(voucherAssignDTO);
		outputView.print(voucherResponseDTO);
	}

	VoucherAssignDTO requestVoucherAssignData() {

		outputView.print(MessageFormat.format(requestMessageFormat, "ID of a customer"));
		long customerId = Long.parseLong(inputView.input());

		outputView.print(MessageFormat.format(requestMessageFormat, "ID of a voucher"));
		long voucherId = Long.parseLong(inputView.input());

		return new VoucherAssignDTO(customerId, voucherId);
	}

	public void deleteVoucherOfCustomer() {
		listVoucherOfCustomer();
		deleteVoucher();
	}

	public void listVoucherOfCustomer() {
		outputView.print(MessageFormat.format(requestMessageFormat, "customer id"));
		long customerId = Long.parseLong(inputView.input());
		List<VoucherResponseDTO> voucherResponseDTOS = voucherService.inquiryVoucherOf(customerId);
		outputView.print(voucherResponseDTOS);
	}

}
