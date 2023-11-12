package com.programmers.springbasic.command;

import static com.programmers.springbasic.console.constants.MessageConstants.*;

import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.programmers.springbasic.console.ConsoleInputHandler;
import com.programmers.springbasic.console.ConsoleOutputHandler;
import com.programmers.springbasic.console.constants.CustomerCommandType;

@Component
public class CustomerCommandExecutor implements Command {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(CustomerCommandExecutor.class);

	private final ConsoleOutputHandler consoleOutputHandler;
	private final ConsoleInputHandler consoleInputHandler;
	private final ApplicationContext applicationContext;

	public CustomerCommandExecutor(ConsoleOutputHandler consoleOutputHandler, ConsoleInputHandler consoleInputHandler,
		ApplicationContext applicationContext) {
		this.consoleOutputHandler = consoleOutputHandler;
		this.consoleInputHandler = consoleInputHandler;
		this.applicationContext = applicationContext;
	}

	@Override
	public void execute() {
		while (true) {
			try {
				consoleOutputHandler.print(CUSTOMER_MENU);
				String userInput = consoleInputHandler.readString();
				CustomerCommandType customerCommandType = CustomerCommandType.from(userInput);

				if (customerCommandType == CustomerCommandType.RETURN_TO_MAIN) {
					return;
				}

				Command command = applicationContext.getBean(customerCommandType.getCommandClass());
				command.execute();
			} catch (IllegalArgumentException e) {
				logger.warn(e.getMessage());
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
	}
}
