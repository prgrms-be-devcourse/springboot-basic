package co.programmers.voucher_management.voucher.controller;

import java.text.MessageFormat;

import org.springframework.stereotype.Controller;

import co.programmers.voucher_management.common.Response;
import co.programmers.voucher_management.customer.service.CustomerService;
import co.programmers.voucher_management.exception.EmptyAssignerException;
import co.programmers.voucher_management.exception.InvalidDataException;
import co.programmers.voucher_management.exception.NoSuchDataException;
import co.programmers.voucher_management.view.InputView;
import co.programmers.voucher_management.view.OutputView;
import co.programmers.voucher_management.voucher.dto.VoucherAssignDTO;
import co.programmers.voucher_management.voucher.dto.VoucherRequestDTO;
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

	public Response executeVoucherMenu(String commandNum) {
		switch (commandNum) {
			case "1":
				return createVoucher();
			case "2":
				return inquiryVoucherOf();
			case "3":
				return updateVoucher();
			case "4":
				return deleteVoucher();
			case "5":
				return assignVoucher();
			case "6":
				return listVoucherOfCustomer();
			case "7":
				return deleteVoucherOfCustomer();
			case "8":
				return inquiryByVoucherId();
			default:
				return new Response(Response.State.FAILED, "Unsupported menu");
		}
	}

	public Response<String> createVoucher() {
		try {
			VoucherRequestDTO voucherRequestDTO = requestVoucherCreationData();
			return voucherService.create(voucherRequestDTO);
		} catch (InvalidDataException | NumberFormatException exception) {
			return new Response<>(Response.State.FAILED, exception.getMessage());
		}
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

	public Response inquiryVoucherOf() {
		try {
			return voucherService.inquiryVoucherOf();
		} catch (RuntimeException runtimeException) {
			return new Response(Response.State.FAILED, runtimeException.getMessage());
		}
	}

	public Response updateVoucher() {
		try {
			VoucherUpdateDTO voucherUpdateDTO = requestVoucherUpdateData();
			return voucherService.update(voucherUpdateDTO);
		} catch (RuntimeException exception) {
			return new Response<>(Response.State.FAILED, exception.getMessage());
		}
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

	public Response deleteVoucher() {
		outputView.print(MessageFormat.format(requestMessageFormat, "ID of a voucher"));
		try {
			Long id = Long.parseLong(inputView.input());
			return voucherService.deleteById(id);
		} catch (NoSuchDataException exception) {
			return new Response<>(Response.State.FAILED, exception.getMessage());
		}
	}

	public Response assignVoucher() {
		try {
			VoucherAssignDTO voucherAssignDTO = requestVoucherAssignData();
			return voucherService.assignVoucher(voucherAssignDTO);
		} catch (RuntimeException exception) {
			return new Response(Response.State.FAILED, exception.getMessage());
		}
	}

	VoucherAssignDTO requestVoucherAssignData() {

		outputView.print(MessageFormat.format(requestMessageFormat, "ID of a customer"));
		long customerId = Long.parseLong(inputView.input());

		outputView.print(MessageFormat.format(requestMessageFormat, "ID of a voucher"));
		long voucherId = Long.parseLong(inputView.input());

		return new VoucherAssignDTO(customerId, voucherId);
	}

	public Response inquiryByVoucherId() {
		outputView.print(MessageFormat.format(requestMessageFormat, "ID of a voucher"));
		long voucherId = Long.parseLong(inputView.input());
		try {
			return customerService.inquiryCustomerByVoucher(voucherId);
		} catch (EmptyAssignerException | NoSuchDataException exception) {
			return new Response(Response.State.FAILED, exception.getMessage());
		}
	}

	public Response deleteVoucherOfCustomer() {
		Response listedVoucher = listVoucherOfCustomer();
		outputView.print(listedVoucher);

		return deleteVoucher();
	}

	public Response listVoucherOfCustomer() {
		outputView.print(MessageFormat.format(requestMessageFormat, "customer id"));
		long customerId = Long.parseLong(inputView.input());
		return voucherService.inquiryVoucherOf(customerId);
	}

}
