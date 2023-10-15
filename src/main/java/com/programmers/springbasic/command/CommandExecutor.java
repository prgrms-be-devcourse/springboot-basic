package com.programmers.springbasic.command;

import org.springframework.stereotype.Component;

import com.programmers.springbasic.console.ConsoleInputHandler;
import com.programmers.springbasic.console.ConsoleOutputHandler;
import com.programmers.springbasic.entity.MenuType;

@Component
public class CommandExecutor {
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
			consoleOutputHandler.printMainMenu();
			MenuType menu = consoleInputHandler.readMenu();
			if (menu == MenuType.EXIT)
				break;
			Command command = commandFactory.getCommand(menu);
			command.execute();
		}
	}
}
