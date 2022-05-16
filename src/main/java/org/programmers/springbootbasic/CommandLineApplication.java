package org.programmers.springbootbasic;

import org.programmers.springbootbasic.command.CommandType;
import org.programmers.springbootbasic.io.Console;
import org.programmers.springbootbasic.voucher.service.DefaultVoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

public class CommandLineApplication implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(CommandLineApplication.class);

	private final Console console;
	private final ApplicationContext applicationContext;

	public CommandLineApplication(Console console, ApplicationContext applicationContext) {
		this.console = console;
		this.applicationContext = applicationContext;
	}

	@Override
	public void run() {
		DefaultVoucherService defaultVoucherService = applicationContext.getBean(DefaultVoucherService.class);
		boolean runProgram = true;

		while (runProgram) {
			try {
				console.printMenu();
				String choiceCommandType = console.input("");
				runProgram = CommandType.execute(
						CommandType.getCreate(choiceCommandType), console, defaultVoucherService);
			} catch (IllegalArgumentException exception) {
				logger.error("Invalid Command Type", exception);
			}
		}
	}
}
