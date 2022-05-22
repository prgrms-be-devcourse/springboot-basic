package org.programmers.springbootbasic.core;

import org.programmers.springbootbasic.core.command.CommandType;
import org.programmers.springbootbasic.core.io.Console;
import org.programmers.springbootbasic.application.voucher.service.DefaultVoucherService;
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
