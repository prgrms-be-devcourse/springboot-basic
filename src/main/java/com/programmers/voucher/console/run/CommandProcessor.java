package com.programmers.voucher.console.run;

import static com.programmers.voucher.console.run.Message.*;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.programmers.voucher.console.io.Input;
import com.programmers.voucher.console.io.Output;
import com.programmers.voucher.domain.customer.model.Customer;
import com.programmers.voucher.domain.customer.model.CustomerType;
import com.programmers.voucher.domain.customer.service.CustomerService;
import com.programmers.voucher.domain.voucher.model.Voucher;
import com.programmers.voucher.domain.voucher.model.VoucherType;
import com.programmers.voucher.domain.voucher.service.VoucherService;
import com.programmers.voucher.domain.wallet.service.WalletService;

@Component
public class CommandProcessor {

	private final VoucherService voucherService;
	private final CustomerService customerService;
	private final WalletService walletService;

	public CommandProcessor(VoucherService voucherService,
		CustomerService customerService, WalletService walletService) {
		this.voucherService = voucherService;
		this.customerService = customerService;
		this.walletService = walletService;
	}

	public void doCommand(Input input, Output output, Command command) {
		switch (command) {
			case CREATE_VOUCHER -> createVoucher(input, output);
			case REGISTER_CUSTOMER -> registerCustomer(input, output);
			case ALLOCATE_VOUCHER -> allocateVoucher(input, output);
			case REMOVE_VOUCHER -> removeVoucher(input, output);
			case GET_VOUCHERS_BY_CUSTOMER -> getVouchersByCustomerId(input, output);
			case GET_CUSTOMERS_BY_VOUCHER -> getCustomersByVoucherId(input, output);
			case GET_ALL_VOUCHER -> getAllVoucher(output);
			case GET_ALL_CUSTOMER -> getAllCustomer(output);
			case GET_ALL_BLACKLIST -> getAllBlacklist(output);
			case EXIT -> exit();
		}
	}

	private void createVoucher(Input input, Output output) {
		output.write(VOUCHER_OPTION.getMessage());
		VoucherType chosenVoucherType = VoucherType.getVoucherType(input.read());
		output.write(DISCOUNT_OPTION.getMessage());
		String discount = input.read();
		voucherService.createVoucher(chosenVoucherType, discount);
	}

	private void registerCustomer(Input input, Output output) {
		output.write(CUSTOMER_OPTION.getMessage());
		CustomerType chosenCustomerType = CustomerType.getCustomerType(input.read());
		customerService.createCustomer(chosenCustomerType);
	}

	private void allocateVoucher(Input input, Output output) {
		getAllCustomer(output);
		output.write(CUSTOMER_SELECT_OPTION.getMessage());
		UUID customerId = UUID.fromString(input.read());
		Customer customer = customerService.findById(customerId);
		getAllVoucher(output);
		output.write(VOUCHER_SELECT_OPTION.getMessage());
		UUID voucherId = UUID.fromString(input.read());
		Voucher voucher = voucherService.findById(voucherId);
		walletService.register(voucher, customer);
	}

	private void removeVoucher(Input input, Output output) {
		getAllCustomer(output);
		output.write(CUSTOMER_SELECT_OPTION.getMessage());
		UUID customerId = UUID.fromString(input.read());
		walletService.deleteByCustomerId(customerId);
	}

	private void getVouchersByCustomerId(Input input, Output output) {
		getAllCustomer(output);
		output.write(CUSTOMER_SELECT_OPTION.getMessage());
		UUID customerId = UUID.fromString(input.read());
		List<Voucher> vouchers = walletService.findVouchersByCustomerId(customerId);
		vouchers.forEach(voucher -> output.write(voucher.toString()));
	}

	private void getCustomersByVoucherId(Input input, Output output) {
		getAllVoucher(output);
		output.write(VOUCHER_SELECT_OPTION.getMessage());
		UUID voucherId = UUID.fromString(input.read());
		List<Customer> customers = walletService.findCustomersByVoucherId(voucherId);
		customers.forEach(customer -> output.write(customer.toString()));
	}

	private void getAllVoucher(Output output) {
		List<Voucher> vouchers = voucherService.getAllVoucher();
		vouchers.forEach(voucher -> output.write(voucher.toString()));
	}

	private void getAllCustomer(Output output) {
		List<Customer> customers = customerService.getAllCustomer();
		customers.forEach(customer -> output.write(customer.toString()));
	}

	private void getAllBlacklist(Output output) {
		List<Customer> customers = customerService.getBlackList();
		customers.forEach(customer -> output.write(customer.toString()));
	}

	private void exit() {
		AppPower.stop();
	}
}
