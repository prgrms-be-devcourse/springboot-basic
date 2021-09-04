package org.programmers.kdt;

import org.programmers.kdt.command.Command;
import org.programmers.kdt.customer.Customer;
import org.programmers.kdt.customer.service.CustomerService;
import org.programmers.kdt.io.Input;
import org.programmers.kdt.io.Output;
import org.programmers.kdt.utils.DigitUtils;
import org.programmers.kdt.voucher.Voucher;
import org.programmers.kdt.voucher.service.VoucherService;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class CustomerVoucherManagingApplication implements Runnable {
	private final Input input;
	private final Output output;
	private final VoucherService voucherService;
	private final CustomerService customerService;

	private final String requestCommandMessage = MessageFormat.format(
            """
     
					=== Customer-Voucher Managing Program ===
					[Type {0} to exit this program]
					Type {1} to list all customer.
					Type {2} to register a voucher to a customer
					Type {3} to deregister a voucher from a customer
					Type {4} to find customer who has a certain voucher
					Type {5} to create a customer
					""",
			Command.EXIT, Command.LIST, Command.REGISTER, Command.DEREGISTER, Command.FIND_CUSTOMER_BY_VOUCHER, Command.CREATE
			);

	public CustomerVoucherManagingApplication(Input input, Output output, VoucherService voucherService, CustomerService customerService) {
		this.input = input;
		this.output = output;
		this.voucherService = voucherService;
		this.customerService = customerService;
	}

	private void printCustomres(List<Customer> customers) {
		output.print("<< Available Customer List >>");
		int size = customers.size();
		for (int i = 0; i < size; i++) {
			output.print(MessageFormat.format("({0}) ---------\n ({1})\n---------\n",
					i, customerService.getPrintFormat(customers.get(i))));
		}
	}

	private int getValidIndexOf(List<?> list, String message) {
		int size = list.size();
		String index =
				input.input(MessageFormat.format(
						"{0} [Range from {1} to {2}]",
						message, 0, size - 1));

		while (!DigitUtils.isDigits(index) ||
				Integer.parseInt(index) < 0 ||
				size <= Integer.parseInt(index)) {
			output.inputError("Invalid range of customer number");
			index =
					input.input(MessageFormat.format(
							"{0} [Range from {1} to {2}]",
							message, 0, size - 1));
		}

		return Integer.parseInt(index);
	}

	private void printAllVoucherOfCustomer(Customer customer) {
		List<Voucher> vouchers = customerService.getAllVoucherOf(customer);
		int size = vouchers.size();
		if(size == 0) {
			output.print("This customer has no voucher yet.");
		} else {
			output.print("<< Voucher list of the customer >>");
			for (int i = 0; i < size; i++) {
				output.print(MessageFormat.format("({0}) ---------\n ({1})\n---------\n",
						i, voucherService.getPrintFormat(vouchers.get(i))));
			}
		}
	}

	private void printVouchers(List<Voucher> vouchers) {
		output.print("<< Available Voucher List >>");
		int size = vouchers.size();
		if (size == 0) {
			output.print("There is no vouchers. Please create voucher first");
		}

		for (int i = 0; i < size; i++) {
			output.print(MessageFormat.format("({0}) ---------\n ({1})\n---------\n",
					i, voucherService.getPrintFormat(vouchers.get(i))));
		}
	}

	@Override
	public void run() {
		boolean termi = false;

		while (!termi) {
			Command command;
			List<Customer> customers = customerService.findAll();
			if (customers.size() == 0) {
				output.print("No Customer has been created yet.");
				output.print("Please create a customer first.");
				output.print("Auto redirecting to customer creation page...");
				command = Command.of("create");
			} else {
				command = Command.getCommandFromInput(requestCommandMessage,
						Command.getCustomerVoucherManagingApplicationCommand(), input, output);
			}

			if (command == null) continue;

			List<Voucher> unregisteredVouchers = voucherService.getAllUnregisteredVouchers();
			List<Voucher> vouchers = voucherService.getAllVouchers();

			switch (command) {
				case LIST -> {
					// 1. show all customer.
					printCustomres(customers);
					int customerIndexInput = getValidIndexOf(customers, "Which customer is that you want to see the voucher wallet?");
					Customer chosenCustomer = customers.get(customerIndexInput);

					// 2. show all voucher of the customer.
					printAllVoucherOfCustomer(chosenCustomer);
				}
				case REGISTER -> {
					// 1. show all unregistered voucher.
					printVouchers(unregisteredVouchers);

					// 2. choose a voucher you want to register
					int voucherIndexInput = getValidIndexOf(unregisteredVouchers, "Choose a voucher you want to register to.");
					Voucher voucher = unregisteredVouchers.get(voucherIndexInput);

					// 3. show all customers
					printCustomres(customers);

					// 4. choose a customer you want to register a voucher to.
					int customerIndexInput = getValidIndexOf(customers, "Choose a customer you want to register the voucher to.");
					Customer chosenCustomer = customers.get(customerIndexInput);

					// 5. register a voucher to a customer
					customerService.addVoucherToCustomer(chosenCustomer, voucher);
					output.printSuccessMessage();
				}
				case DEREGISTER -> {
					// 1. show all customer.
					printCustomres(customers);

					// 2. choose a customer that you want to remove a voucher from.
					int customerIndexInput = getValidIndexOf(customers, "Which customer is that you want to see the voucher wallet?");
					Customer chosenCustomer = customers.get(customerIndexInput);

					// 3. show all voucher belongs to the customer
					printAllVoucherOfCustomer(chosenCustomer);

					// 4. choose a voucher that you want to remove from the customer.
					List<Voucher> ownedVouchers = voucherService.getAllVouchersBelongsToCustomer(chosenCustomer);
					int voucherIndexInput = getValidIndexOf(ownedVouchers, "Choose a voucher you want to deregister from.");
					Voucher chosenVoucher = ownedVouchers.get(voucherIndexInput);

					// 5. remove the voucher from the customer.
					customerService.removeVoucherFromCustomer(chosenCustomer, chosenVoucher);
					output.printSuccessMessage();
				}
				case FIND_CUSTOMER_BY_VOUCHER -> {
					// 1. show all voucher
					printVouchers(vouchers);

					// 2. choose a voucher you want to find its owner.
					int voucherIndexInput = getValidIndexOf(vouchers, "Choose a voucher you want to find its owner.");
					Voucher chosenVoucher = vouchers.get(voucherIndexInput);

					// 3. find the owner of the voucher
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

					// 4. show the owner info.
					Customer customer = customerHoldingVoucher.get();
					output.print(MessageFormat.format("<< Voucher Owner Info >>\n{0}", customerService.getPrintFormat(customer)));
				}
				case CREATE -> {
					String name = input.input("Enter your name.");
					String email = input.input("Enter your email.");

					Customer customer = customerService.signUp(name, email);
					output.print("Welcome! Your are now our member!");
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
