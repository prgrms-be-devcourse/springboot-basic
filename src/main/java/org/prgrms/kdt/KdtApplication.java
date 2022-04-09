package org.prgrms.kdt;

import org.prgrms.kdt.domain.command.types.CommandType;
import org.prgrms.kdt.domain.command.controller.CommandController;
import org.prgrms.kdt.util.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class KdtApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(KdtApplication.class, args);
		CommandController commandController = context.getBean(CommandController.class);
		Config config = context.getBean(Config.class);
		String commandInput;

		if(config.getProfile().equals("dev")){
			context.getEnvironment().setActiveProfiles("dev");
		}

		do {
			commandInput = Console.inputCommand();
			commandController.processCommand(commandInput);
		} while (!CommandType.isExit(commandInput));
	}
}
