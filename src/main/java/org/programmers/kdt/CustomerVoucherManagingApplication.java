package org.programmers.kdt;

import org.programmers.kdt.command.Command;
import org.programmers.kdt.customer.Customer;
import org.programmers.kdt.customer.service.CustomerService;
import org.programmers.kdt.io.Input;
import org.programmers.kdt.io.Output;
import org.programmers.kdt.utils.DigitUtils;
import org.programmers.kdt.voucher.Voucher;
import org.programmers.kdt.voucher.service.VoucherService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class CustomerVoucherManagingApplication implements Runnable {
	// FIXME: 너무 중복이 많음 -> 계속 개선!!
	private AnnotationConfigApplicationContext applicationContext;
	private final Input input;
	private final Output output;

	private final String requestCommandMessage = MessageFormat.format("\n=== Customer-Voucher Managing Program ===" +
			"\n[Type {0} to exit this program]" +
			"\nType {1} to list all customer." +
			"\nType {2} to register a voucher to a customer" +
			"\nType {3} to deregister a voucher from a customer" +
			"\nType {4} to find customer who has a certain voucher" +
			"\nType {5} to create a customer",
			Command.EXIT, Command.LIST, Command.REGISTER, Command.DEREGISTER, Command.FIND_CUSTOMER_BY_VOUCHER, Command.CREATE
			);
	private final String requestCommandMeesage
			= MessageFormat.format("\n=== Voucher Program ===" +
					"\n[Type {0} to exit the program]" +
					"\nType {1} to create a voucher." +
					"\nType {2} to list all voucher." +
					"\nType {3} to enter customer managing page.",
			Command.EXIT, Command.CREATE, Command.LIST, Command.CUSTOMER);

	public CustomerVoucherManagingApplication(AnnotationConfigApplicationContext applicationContext, Input input, Output output) {
		this.applicationContext = applicationContext;
		this.input = input;
		this.output = output;
	}

	@Override
	public void run() {
		VoucherService voucherService = applicationContext.getBean(VoucherService.class);
		CustomerService customerService = applicationContext.getBean(CustomerService.class);

		boolean termi = false;

		while (!termi) {
			Command command = Command.getCommand(requestCommandMessage, Command.getCustomerVoucherManagingApplicationCommand(),
					input, output);
			if (command == null) continue;

			switch (command) {
				case LIST -> {
					List<Customer> all = customerService.findAll();
					int size = all.size();
					if (size == 0) {
						output.print("No Customer has been created yet.");
						output.print("Back to Front Page...");
						termi = true;
						break;
					}

					output.print("<< Current Active Customer List >>");
					for (int i = 0; i < size; i++) {
						output.print(MessageFormat.format("({0}) ---------\n ({1})\n---------\n",
								i, customerService.getPrintFormat(all.get(i))));
					}

					String chosenCustomerIndex =
							input.input(MessageFormat.format(
									"Which customer is that you want to see the voucher wallet? [{0}, {1}]",
									0, size - 1));

					while (!DigitUtils.isDigits(chosenCustomerIndex) ||
							Integer.parseInt(chosenCustomerIndex) < 0 ||
							size <= Integer.parseInt(chosenCustomerIndex)) {
						output.inputError("Invalid range of customer number");
						chosenCustomerIndex =
								input.input(MessageFormat.format(
										"Which customer is that you want to see the voucher wallet? [{0}, {1}]",
										0, size - 1));
					}

					Customer chosenCustomer = all.get(Integer.parseInt(chosenCustomerIndex));
					List<Voucher> vouchers = customerService.getAllVoucherOf(chosenCustomer);

					for (Voucher voucher : vouchers) {
						output.print(voucherService.getPrintFormat(voucher));
					}
				}
				case REGISTER -> {
					// 1. show all unregistered vouchers
					List<Voucher> allUnregisteredVoucher = voucherService.getAllUnregisteredVouchers();
					output.print("<< Unregistered Vouchers >>");
					int numberOfVouchers = allUnregisteredVoucher.size();
					if (numberOfVouchers == 0) {
						output.print("There is no vouchers. Please create voucher first");
						termi = true;
						break;
					}

					for (int i = 0; i < numberOfVouchers; i++) {
						Voucher voucher = allUnregisteredVoucher.get(i);
						output.print(MessageFormat.format("({0}) ---------\n ({1})\n---------\n",
								i, voucherService.getPrintFormat(voucher)));
					}

					String voucherIndex = input.input(MessageFormat.format(
							"Choose a voucher you want to register to. [{0}, {1}]", 0, numberOfVouchers - 1));
					while (!DigitUtils.isDigits(voucherIndex) ||
							Integer.parseInt(voucherIndex) < 0 ||
							numberOfVouchers <= Integer.parseInt(voucherIndex)) {
						output.inputError("Invalid range of customer number");
						voucherIndex =
								input.input(MessageFormat.format(
										"Choose a voucher you want to register to. [{0}, {1}]", 0, numberOfVouchers - 1));
					}

					Voucher voucher = allUnregisteredVoucher.get(Integer.parseInt(voucherIndex));

					// 2. show all customer
					List<Customer> allCustomer = customerService.findAll();
					int numberofCustomers = allCustomer.size();
					if (numberofCustomers == 0) {
						output.print("No Customer has been created yet.");
						output.print("Please create a customer first");
						output.print("Back to Front Page...");
						termi = true;
						break;
					}

					output.print("<< Current Active Customer List >>");
					for (int i = 0; i < numberofCustomers; i++) {
						output.print(MessageFormat.format("({0}) ---------\n ({1})\n---------\n",
								i, customerService.getPrintFormat(allCustomer.get(i))));
					}

					String chosenCustomerIndex =
							input.input(MessageFormat.format(
									"Choose a customer you want to register the voucher to. [{0}, {1}]",
									0, numberofCustomers - 1));

					while (!DigitUtils.isDigits(chosenCustomerIndex) ||
							Integer.parseInt(chosenCustomerIndex) < 0 ||
							numberofCustomers <= Integer.parseInt(chosenCustomerIndex)) {
						output.inputError("Invalid range of customer number");
						chosenCustomerIndex =
								input.input(MessageFormat.format(
										"Choose a customer you want to register the voucher to. [{0}, {1}]",
										0, numberOfVouchers - 1));
					}

					Customer customer = allCustomer.get(Integer.parseInt(chosenCustomerIndex));

					// 3. register a voucher to a customer
					customerService.addVoucherToCustomer(customer, voucher);
				}
				case DEREGISTER -> {
					// FIXME: LIST와 많은 부분이 중복 -> 제거
					List<Customer> all = customerService.findAll();
					int numberOfCustomers = all.size();
					if (numberOfCustomers == 0) {
						output.print("No Customer has been created yet.");
						output.print("Back to Front Page...");
						termi = true;
						break;
					}

					output.print("<< Current Active Customer List >>");
					for (int i = 0; i < numberOfCustomers; i++) {
						output.print(MessageFormat.format("({0}) ---------\n ({1})\n---------\n",
								i, customerService.getPrintFormat(all.get(i))));
					}

					String chosenCustomerIndex =
							input.input(MessageFormat.format(
									"Which customer is that you want to see the voucher wallet? [{0}, {1}]",
									0, numberOfCustomers - 1));

					while (!DigitUtils.isDigits(chosenCustomerIndex) ||
							Integer.parseInt(chosenCustomerIndex) < 0 ||
							numberOfCustomers <= Integer.parseInt(chosenCustomerIndex)) {
						output.inputError("Invalid range of customer number");
						chosenCustomerIndex =
								input.input(MessageFormat.format(
										"Which customer is that you want to see the voucher wallet? [{0}, {1}]",
										0, numberOfCustomers - 1));
					}

					Customer chosenCustomer = all.get(Integer.parseInt(chosenCustomerIndex));

					List<Voucher> vouchers = customerService.getAllVoucherOf(chosenCustomer);
					int numberOfVouchers = vouchers.size();
					if(numberOfVouchers == 0) {
						output.print("This customer has no voucher yet. Return to the front page");
						termi = true;
						break;
					}

					output.print("<< Voucher list of the chosen customer");
					for (int i = 0; i < numberOfVouchers; i++) {
						output.print(MessageFormat.format("({0}) ---------\n ({1})\n---------\n",
								i, voucherService.getPrintFormat(vouchers.get(i))));
					}

					String chosenVoucherIndex =
							input.input(MessageFormat.format(
									"Choose a voucher you want to deregister from. [{0}, {1}]",
									0, numberOfVouchers - 1));

					while (!DigitUtils.isDigits(chosenVoucherIndex) ||
							Integer.parseInt(chosenVoucherIndex) < 0 ||
							numberOfVouchers <= Integer.parseInt(chosenVoucherIndex)) {
						output.inputError("Invalid range of voucher number");
						chosenVoucherIndex =
								input.input(MessageFormat.format(
										"Choose a voucher you want to deregister from. [{0}, {1}]",
										0, numberOfVouchers - 1));
					}
					Voucher chosenVoucher = vouchers.get(Integer.parseInt(chosenVoucherIndex));

					customerService.removeVoucherFromCustomer(chosenCustomer, chosenVoucher);
				}
				case FIND_CUSTOMER_BY_VOUCHER -> {
					// 1. show all voucher
					output.print("<< All Created Voucher List >>");
					List<Voucher> vouchers = voucherService.getAllVouchers();
					int numberOfVouchers = vouchers.size();

					for (int i = 0; i < numberOfVouchers; i++) {
						output.print(MessageFormat.format("({0}) ---------\n ({1})\n---------\n",
								i, voucherService.getPrintFormat(vouchers.get(i))));
					}

					// 2. choose a voucher
					String chosenVoucherIndex =
							input.input(MessageFormat.format(
									"Choose a voucher you want to find its owner. [{0}, {1}]",
									0, numberOfVouchers - 1));

					while (!DigitUtils.isDigits(chosenVoucherIndex) ||
							Integer.parseInt(chosenVoucherIndex) < 0 ||
							numberOfVouchers <= Integer.parseInt(chosenVoucherIndex)) {
						output.inputError("Invalid range of voucher number");
						chosenVoucherIndex =
								input.input(MessageFormat.format(
										"Choose a voucher you want to find its owner. [{0}, {1}]",
										0, numberOfVouchers - 1));
					}

					Voucher chosenVoucher = vouchers.get(Integer.parseInt(chosenVoucherIndex));

					// 3. show its owner
					Optional<UUID> customerIdHoldingVoucher = voucherService.findCustomerIdHoldingVoucherOf(chosenVoucher);
					if (customerIdHoldingVoucher.isEmpty()) {
						output.print("The voucher is not registered to a customer yet");
						termi = true;
						break;
					}

					UUID customerId = customerIdHoldingVoucher.get();
					Optional<Customer> customerHoldingVoucher = customerService.findCustomerById(customerId);
					if (customerHoldingVoucher.isEmpty()) {
						output.print("The customer who had owned the voucher is no more our member.");
						termi = true;
						break;
					}

					Customer customer = customerHoldingVoucher.get();
					output.print(MessageFormat.format("<< Voucher Owner Info >>\n{0}", customerService.getPrintFormat(customer)));
				}
				case CREATE -> {
					// 1. create a customer
					String name = input.input("Enter your name.");
					String email = input.input("Enter your email.");

					Customer customer = customerService.signUp(name, email);
					output.print("Welcome! Your are now our member.");
					output.print(customerService.getPrintFormat(customer));
				}
				case EXIT -> {
					output.sayGoodBye();
					termi = true;
				}
			}
		}
	}
}
