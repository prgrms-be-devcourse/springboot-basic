package com.kdt.commandLineApp;

import com.kdt.commandLineApp.customer.CustomerService;
import com.kdt.commandLineApp.io.IO;
import com.kdt.commandLineApp.io.commandLineAppIO;
import com.kdt.commandLineApp.voucher.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class CommandLineApplication {
	private static final Logger logger = LoggerFactory.getLogger(CommandLineApplication.class);

	public static void main(String[] args) {
		CommandLineApplication commandLineApplication = new CommandLineApplication();
		ApplicationContext voucherContext = new AnnotationConfigApplicationContext(AppContext.class);
		VoucherService voucherService = voucherContext.getBean("voucherService",VoucherService.class);
		CustomerService customerService = voucherContext.getBean("customerService", CustomerService.class);
		IO io = voucherContext.getBean("commandLineAppIO", commandLineAppIO.class);

		while (true) {
			io.print("=== Voucher Program ===\n" +
					"Type exit to exit the program.\n" +
					"Type create to create a new voucher.\n" +
					"Type list to list all vouchers.\n" +
					"Type blacklist to list all blacklist custom info"
			);
			try {
				String command = io.get();
				switch (command) {
					case "create":
						io.print("Type voucher type(fixed or percent) and amount");
						String voucherType = io.get();
						float amount = Float.parseFloat(io.get());
						voucherService.addVoucher(voucherType, amount);
						break;
					case "list":
						io.print(voucherService.getVouchers());
						break;
					case "blacklist":
						io.print(customerService.getCustomers());
						break;
					case "exit":
						logger.debug("successfully exit");
						return;
					default:
						io.print("Type correct command");
				}
			}
			catch (Exception e) {
				logger.debug(e.getMessage());
			}
		}
	}
}
