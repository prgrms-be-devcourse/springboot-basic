package org.prgrms.kdt;

import org.prgrms.kdt.domain.command.types.CommandType;
import org.prgrms.kdt.domain.command.componet.CommandApplication;
import org.prgrms.kdt.domain.console.Input;
import org.prgrms.kdt.domain.console.Output;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class KdtApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(KdtApplication.class, args);
		CommandApplication commandApplication = context.getBean(CommandApplication.class);
		CommandType commandType;

		do {
			Output.printVoucherProgram();
			commandType = Input.inputVoucherCommand();
			commandApplication.processCommand(commandType);
		} while (commandType != CommandType.EXIT);
	}
}
