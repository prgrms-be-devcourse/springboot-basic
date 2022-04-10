package org.prgrms.kdt;

import org.prgrms.kdt.domain.command.types.CommandType;
import org.prgrms.kdt.domain.voucher.controller.VoucherController;
import org.prgrms.kdt.util.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class KdtApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(KdtApplication.class, args);
		VoucherController voucherController = context.getBean(VoucherController.class);
		Config config = context.getBean(Config.class);
		CommandType commandType;

		if(config.getProfile().equals("dev")){
			context.getEnvironment().setActiveProfiles("dev");
		}

		do {
			String commandInput = Console.inputCommand();
			commandType = CommandType.findCommand(commandInput);
			voucherController.processCommand(commandType);
		} while (commandType != CommandType.EXIT);
	}
}
