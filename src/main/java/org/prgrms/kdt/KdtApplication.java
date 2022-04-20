package org.prgrms.kdt;

import org.prgrms.kdt.domain.command.CommandType;
import org.prgrms.kdt.domain.command.CommandProcessor;
import org.prgrms.kdt.util.Input;
import org.prgrms.kdt.util.Output;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class KdtApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(KdtApplication.class, args);
		CommandProcessor commandProcessor = context.getBean(CommandProcessor.class);
		CommandType commandType;

		do {
			Output.printVoucherProgram();
			commandType = Input.inputVoucherCommand();
			commandProcessor.doCommand(commandType);
		} while (commandType != CommandType.EXIT);
	}
}
