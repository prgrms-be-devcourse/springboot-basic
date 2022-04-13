package org.programmers.springbootbasic;

import org.programmers.springbootbasic.customer.service.CustomerService;
import org.programmers.springbootbasic.io.Console;
import org.programmers.springbootbasic.voucher.model.Voucher;
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
					long value = validLong(console.input("Type value"));
					if (value <= 0) {
						break;
					}
					String type = console.input("Type Voucher\n 1.FixedAmountVoucher\n 2.PercentDiscountVoucher");
					try {
						Voucher voucher = voucherService.createVoucher(value, type);
						if (voucher == null) {
							logger.error("wrong voucher type input error");
							break;
						}
						voucherService.saveVoucher(voucher);
						console.printSuccessMessage();
					} catch (IllegalArgumentException exception) {
						logger.error("wrong number input error");
					}
				}
				case "list" -> voucherService.findAll().forEach(System.out::println);
				case "blacklist" -> customerService.findAll().forEach(System.out::println);
				default -> logger.error("wrong input error");
			}
		}
	}

	private long validLong (String input) {
		long value = -1L;
		try {
			value = Long.parseLong(input);
		}
		catch (NumberFormatException exception) {
			logger.error("wrong long type input error");
		}
		return value;
	}
}
