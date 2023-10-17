package com.programmers.springbasic.command;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import com.programmers.springbasic.console.ConsoleInputHandler;
import com.programmers.springbasic.console.ConsoleOutputHandler;
import com.programmers.springbasic.enums.MenuType;

@Component
public class CommandExecutor {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(CommandExecutor.class);
	private final ConsoleOutputHandler consoleOutputHandler;
	private final ConsoleInputHandler consoleInputHandler;
	private final CommandFactory commandFactory;

	public CommandExecutor(ConsoleOutputHandler consoleOutputHandler, ConsoleInputHandler consoleInputHandler, CommandFactory commandFactory) {
		this.consoleOutputHandler = consoleOutputHandler;
		this.consoleInputHandler = consoleInputHandler;
		this.commandFactory = commandFactory;
	}

	public void executeCommands() {
		while (true) {
			try {
				consoleOutputHandler.printMainMenu();
				MenuType menu = MenuType.from(consoleInputHandler.readString());
				if (menu == MenuType.EXIT) {
					System.exit(0);
				}
				Command command = commandFactory.getCommand(menu);
				command.execute();
			} catch (IllegalArgumentException e) {
				logger.warn(e.getMessage());
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
	}
}
