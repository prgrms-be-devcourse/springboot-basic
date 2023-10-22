package com.programmers.springbasic.command;

import org.slf4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.programmers.springbasic.console.ConsoleInputHandler;
import com.programmers.springbasic.console.ConsoleOutputHandler;
import com.programmers.springbasic.enums.CommandType;

@Component
public class CommandExecutor implements CommandLineRunner {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(CommandExecutor.class);

	private final ConsoleOutputHandler consoleOutputHandler;
	private final ConsoleInputHandler consoleInputHandler;
	private final CommandFactory commandFactory;

	public CommandExecutor(ConsoleOutputHandler consoleOutputHandler, ConsoleInputHandler consoleInputHandler,
		CommandFactory commandFactory) {
		this.consoleOutputHandler = consoleOutputHandler;
		this.consoleInputHandler = consoleInputHandler;
		this.commandFactory = commandFactory;
	}

	@Override
	public void run(String... args) {
		while (true) {
			try {
				consoleOutputHandler.printMainCommand();
				CommandType commandType = CommandType.from(consoleInputHandler.readString());
				Command command = commandFactory.getCommand(commandType);
				command.execute();
			} catch (IllegalArgumentException e) {
				logger.warn(e.getMessage());
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
	}
}
