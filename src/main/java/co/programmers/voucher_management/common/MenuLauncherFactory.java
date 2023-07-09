package co.programmers.voucher_management.common;

import java.text.MessageFormat;
import java.util.List;

import org.springframework.stereotype.Controller;

import co.programmers.voucher_management.customer.entity.Customer;
import co.programmers.voucher_management.customer.service.CustomerService;
import co.programmers.voucher_management.exception.DiscountTypeException;
import co.programmers.voucher_management.exception.EmptyAssignerException;
import co.programmers.voucher_management.exception.InvalidVoucherAmountException;
import co.programmers.voucher_management.exception.MenuTypeMismatchException;
import co.programmers.voucher_management.exception.NoSuchDataException;
import co.programmers.voucher_management.view.InputView;
import co.programmers.voucher_management.view.OutputView;
import co.programmers.voucher_management.voucher.dto.VoucherAssignDTO;
import co.programmers.voucher_management.voucher.dto.VoucherRequestDTO;
import co.programmers.voucher_management.voucher.dto.VoucherUpdateDTO;
import co.programmers.voucher_management.voucher.service.VoucherService;

@Controller
public class MenuLauncherFactory {
	private final VoucherService voucherService;
	private final CustomerService customerService;
	private final OutputView outputView;
	private final InputView<String> inputView;
	private final String requestMessageFormat = "Input {0} >> ";

	public MenuLauncherFactory(VoucherService voucherService, CustomerService customerService, OutputView outputView,
			InputView<String> inputView) {
		this.voucherService = voucherService;
		this.customerService = customerService;
		this.outputView = outputView;
		this.inputView = inputView;
	}

	public MenuLauncher of(String menu) {
		switch (menu) {
			case "1":
			case "x":
				return this::terminate;
			case "2":
				return this::createVoucher;
			case "3":
				return this::inquiryVoucherOf;
			case "4":
				return this::updateVoucher;
			case "5":
				return this::deleteVoucher;
			case "6":
				return this::inquiryBlackList;
			case "7":
				return this::assignVoucher;
			case "8":
				return this::listVoucherOfCustomer;
			case "9":
				return this::DeleteVoucherOfCustomer;
			case "10":
				return this::inquiryByVoucherId;
			default:
				throw new MenuTypeMismatchException();
		}
	}

	private Response terminate() {
		return new Response<>(Response.State.SUCCESS, List.of());
	}

	private Response inquiryBlackList() {
		String rating = Customer.Rating.BLACKLIST.symbol();
		return customerService.inquireByRating(rating);
	}

	private Response inquiryVoucherOf() {
		try {
			return voucherService.inquiryVoucherOf();
		} catch (RuntimeException runtimeException) {
			return new Response(Response.State.FAILED, runtimeException.getMessage());
		}
	}

	private Response DeleteVoucherOfCustomer() {
		Response listedVoucher = listVoucherOfCustomer();
		outputView.print(listedVoucher);

		return deleteVoucher();
	}

	private Response inquiryByVoucherId() {
		outputView.print(MessageFormat.format(requestMessageFormat, "ID of a voucher"));
		long voucherId = Long.parseLong(inputView.input());
		try {
			return customerService.inquiryCustomerByVoucher(voucherId);
		} catch (EmptyAssignerException | NoSuchDataException exception) {
			return new Response(Response.State.FAILED, exception.getMessage());
		}
	}

	private Response listVoucherOfCustomer() {
		outputView.print(MessageFormat.format(requestMessageFormat, "customer id"));
		long customerId = Long.parseLong(inputView.input());
		return voucherService.inquiryVoucherOf(customerId);
	}

	private Response assignVoucher() {
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

	private Response deleteVoucher() {
		outputView.print(MessageFormat.format(requestMessageFormat, "ID of a voucher"));
		try {
			Long id = Long.parseLong(inputView.input());
			return voucherService.deleteById(id);
		} catch (NoSuchDataException exception) {
			return new Response<>(Response.State.FAILED, exception.getMessage());
		}
	}

	private Response updateVoucher() {
		try {
			VoucherUpdateDTO voucherUpdateDTO = requestVoucherUpdateData();
			return voucherService.update(voucherUpdateDTO);
		} catch (RuntimeException exception) {
			return new Response<>(Response.State.FAILED, exception.getMessage());
		}
	}

	private Response<String> createVoucher() {
		try {
			VoucherRequestDTO voucherRequestDTO = requestVoucherCreationData();
			return voucherService.create(voucherRequestDTO);
		} catch (InvalidVoucherAmountException | DiscountTypeException | NumberFormatException exception) {
			return new Response<>(Response.State.FAILED, exception.getMessage());
		}
	}

	VoucherUpdateDTO requestVoucherUpdateData() {
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

	VoucherRequestDTO requestVoucherCreationData() {
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
}
