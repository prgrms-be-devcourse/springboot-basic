package com.programmers.basic.command;

import org.springframework.stereotype.Component;

import com.programmers.basic.command.Command;
import com.programmers.basic.command.CommandFactory;
import com.programmers.basic.console.OutputHandler;
import com.programmers.basic.console.InputHandler;
import com.programmers.basic.entity.MenuType;

@Component
public class CommandExecutor {
	private final OutputHandler outputHandler;
	private final InputHandler inputHandler;
	private final CommandFactory commandFactory;

	public CommandExecutor(OutputHandler outputHandler, InputHandler inputHandler, CommandFactory commandFactory) {
		this.outputHandler = outputHandler;
		this.inputHandler = inputHandler;
		this.commandFactory = commandFactory;
	}

	public void executeCommands() {
		while (true) {
			outputHandler.printMainMenu();
			MenuType menu = MenuType.valueOf(inputHandler.readString());
			if (menu==MenuType.EXIT)
				break;

			Command command = commandFactory.getCommand(menu);
			command.execute();
		}
	}
}
