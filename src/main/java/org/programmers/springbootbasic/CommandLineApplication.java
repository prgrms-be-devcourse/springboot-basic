package org.programmers.springbootbasic;

import org.programmers.springbootbasic.customer.service.CustomerService;
import org.programmers.springbootbasic.io.Console;
import org.programmers.springbootbasic.voucher.model.VoucherType;
import org.programmers.springbootbasic.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineApplication implements CommandLineRunner {
	private static final Logger logger = LoggerFactory.getLogger(CommandLineApplication.class);

	private final Console console;
	private final VoucherService voucherService;
	private final CustomerService customerService;

	public CommandLineApplication(Console console, VoucherService voucherService, CustomerService customerService) {
		this.console = console;
		this.voucherService = voucherService;
		this.customerService = customerService;
	}

	@Override
	public void run(String... args) {
		boolean exitProgram = false;

		while (!exitProgram) {
			console.printMenu();
			String choice = console.input("");

			switch (choice) {
				case "exit" -> exitProgram = true;
				case "create" -> {
					try {
						long value = Long.parseLong(console.input("Type value"));
						VoucherType voucherType = VoucherType.findByNumber(Integer.parseInt(console.input("Type Voucher number\n 1. FixedAmountVoucher\n 2. PercentDiscountVoucher")));
						voucherService.createVoucher(voucherType, value);
						console.printSuccessMessage();
					} catch (NumberFormatException e) {
						logger.error("wrong long type input error", e);
					} catch (IllegalArgumentException e) {
						logger.error("creat voucher IllegalArgumentException error", e);
					}
				}
				case "list" -> voucherService.getVoucherList().forEach(System.out::println);
				case "blacklist" -> customerService.findAll().forEach(System.out::println);
				default -> logger.error("wrong console choice error");
			}
		}
	}
}
