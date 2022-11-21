package com.programmers.voucher.console.run;

import static com.programmers.voucher.console.run.Message.*;
import static com.programmers.voucher.core.exception.ExceptionMessage.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.programmers.voucher.console.io.Input;
import com.programmers.voucher.console.io.Output;
import com.programmers.voucher.domain.customer.model.Customer;
import com.programmers.voucher.domain.customer.model.CustomerType;
import com.programmers.voucher.domain.customer.service.CustomerService;
import com.programmers.voucher.domain.voucher.model.Voucher;
import com.programmers.voucher.domain.voucher.model.VoucherType;
import com.programmers.voucher.domain.voucher.service.VoucherService;
import com.programmers.voucher.domain.wallet.service.WalletService;

public enum Command {

	CREATE_VOUCHER("create",
		(Input input, Output output, VoucherService voucherService, CustomerService customerService, WalletService walletService) -> {
			output.write(VOUCHER_OPTION.getMessage());
			VoucherType chosenVoucherType = VoucherType.getVoucherType(input.read());
			output.write(DISCOUNT_OPTION.getMessage());
			String discount = input.read();
			voucherService.createVoucher(chosenVoucherType, discount);
		}),
	REGISTER_CUSTOMER("register",
		(Input input, Output output, VoucherService voucherService, CustomerService customerService, WalletService walletService) -> {
			output.write(CUSTOMER_OPTION.getMessage());
			CustomerType chosenCustomerType = CustomerType.getCustomerType(input.read());
			customerService.createCustomer(chosenCustomerType);
		}),
	ALLOCATE_VOUCHER("allocate",
		(Input input, Output output, VoucherService voucherService, CustomerService customerService, WalletService walletService) -> {
			List<Customer> customers = customerService.getAllCustomer();
			customers.forEach(customer -> output.write(customer.toString()));
			output.write(CUSTOMER_SELECT_OPTION.getMessage());
			UUID customerId = UUID.fromString(input.read());
			Customer customer = customerService.findById(customerId);
			List<Voucher> vouchers = voucherService.getAllVoucher();
			vouchers.forEach(voucher -> output.write(voucher.toString()));
			output.write(VOUCHER_SELECT_OPTION.getMessage());
			UUID voucherId = UUID.fromString(input.read());
			Voucher voucher = voucherService.findById(voucherId);
			walletService.register(voucher, customer);
		}),
	REMOVE_VOUCHER("remove",
		(Input input, Output output, VoucherService voucherService, CustomerService customerService, WalletService walletService) -> {
			List<Customer> customers = customerService.getAllCustomer();
			customers.forEach(customer -> output.write(customer.toString()));
			output.write(CUSTOMER_SELECT_OPTION.getMessage());
			UUID customerId = UUID.fromString(input.read());
			walletService.deleteByCustomerId(customerId);
		}),
	GET_VOUCHERS_BY_CUSTOMER("vouchers_by_customer",
		(Input input, Output output, VoucherService voucherService, CustomerService customerService, WalletService walletService) -> {
			List<Customer> customers = customerService.getAllCustomer();
			customers.forEach(customer -> output.write(customer.toString()));
			output.write(CUSTOMER_SELECT_OPTION.getMessage());
			UUID customerId = UUID.fromString(input.read());
			List<Voucher> vouchers = walletService.findVouchersByCustomerId(customerId);
			vouchers.forEach(voucher -> output.write(voucher.toString()));
		}),
	GET_CUSTOMERS_BY_VOUCHER("customers_by_voucher",
		(Input input, Output output, VoucherService voucherService, CustomerService customerService, WalletService walletService) -> {
			List<Voucher> vouchers = voucherService.getAllVoucher();
			vouchers.forEach(voucher -> output.write(voucher.toString()));
			output.write(VOUCHER_SELECT_OPTION.getMessage());
			UUID voucherId = UUID.fromString(input.read());
			List<Customer> customers = walletService.findCustomersByVoucherId(voucherId);
			customers.forEach(customer -> output.write(customer.toString()));
		}),
	GET_ALL_VOUCHER("vouchers",
		(Input input, Output output, VoucherService voucherService, CustomerService customerService, WalletService walletService) -> {
			List<Voucher> vouchers = voucherService.getAllVoucher();
			vouchers.forEach(voucher -> output.write(voucher.toString()));
		}),
	GET_ALL_CUSTOMER("customers",
		(Input input, Output output, VoucherService voucherService, CustomerService customerService, WalletService walletService) -> {
			List<Customer> customers = customerService.getAllCustomer();
			customers.forEach(customer -> output.write(customer.toString()));
		}),
	GET_ALL_BLACKLIST("blacklists",
		(Input input, Output output, VoucherService voucherService, CustomerService customerService, WalletService walletService) -> {
			List<Customer> customers = customerService.getBlackList();
			customers.forEach(customer -> output.write(customer.toString()));
		}),
	EXIT("exit",
		(Input input, Output output, VoucherService voucherService, CustomerService customerService, WalletService walletService) -> {
			AppPower.stop();
		});

	private static final Logger log = LoggerFactory.getLogger(Command.class);
	private final String option;
	private final PentaConsumer<Input, Output, VoucherService, CustomerService, WalletService> commandFunction;

	Command(String option,
		PentaConsumer<Input, Output, VoucherService, CustomerService, WalletService> commandFunction) {
		this.option = option;
		this.commandFunction = commandFunction;
	}

	public static Command getCommand(String chosenCommand) {
		return Arrays.stream(Command.values())
			.filter(command -> command.option.equals(chosenCommand))
			.findFirst()
			.orElseThrow(() -> {
				log.error(WRONG_COMMAND.getMessage());
				throw new IllegalArgumentException(WRONG_COMMAND.getMessage());
			});
	}

	public void doCommand(Input input, Output output, VoucherService voucherService, CustomerService customerService,
		WalletService walletService) {
		commandFunction.apply(input, output, voucherService, customerService, walletService);
	}
}
