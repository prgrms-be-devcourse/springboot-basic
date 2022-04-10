package org.prgrms.kdt;

import org.prgrms.kdt.domain.command.types.CommandType;
import org.prgrms.kdt.domain.voucher.controller.VoucherController;
import org.prgrms.kdt.util.Console;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class KdtApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(KdtApplication.class, args);
		Logger logger = LoggerFactory.getLogger(KdtApplication.class);
		VoucherController voucherController = context.getBean(VoucherController.class);
		CommandType commandType;

		do {
			String commandInput = Console.inputCommand();
			commandType = CommandType.findCommand(commandInput);
			try {
				voucherController.processCommand(commandType);
			} catch (Exception e) {
				logger.error("Process Command failed: {}", e.getMessage());
			}
		} while (commandType != CommandType.EXIT);

		logger.info("Exit Program");
	}
}
