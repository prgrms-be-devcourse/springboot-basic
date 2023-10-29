package com.programmers.springbasic.command;

import static com.programmers.springbasic.constants.MessageConstants.*;

import org.slf4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.programmers.springbasic.console.ConsoleInputHandler;
import com.programmers.springbasic.console.ConsoleOutputHandler;
import com.programmers.springbasic.constants.MainCommandType;

@Component
public class MainCommandExecutor implements CommandLineRunner {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(MainCommandExecutor.class);

	private final ApplicationContext applicationContext;
	private final ConsoleOutputHandler consoleOutputHandler;
	private final ConsoleInputHandler consoleInputHandler;

	public MainCommandExecutor(ApplicationContext applicationContext, ConsoleOutputHandler consoleOutputHandler,
		ConsoleInputHandler consoleInputHandler) {
		this.applicationContext = applicationContext;
		this.consoleOutputHandler = consoleOutputHandler;
		this.consoleInputHandler = consoleInputHandler;
	}

	@Override
	public void run(String... args) {
		while (true) {
			try {
				consoleOutputHandler.print(MAIN_MENU);
				MainCommandType mainCommandType = MainCommandType.from(consoleInputHandler.readString());
				Command command = applicationContext.getBean(mainCommandType.getCommandClass());
				command.execute();
			} catch (IllegalArgumentException e) {
				logger.warn(e.getMessage());
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
	}

}
