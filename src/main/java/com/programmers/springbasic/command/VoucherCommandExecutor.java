package com.programmers.springbasic.command;

import static com.programmers.springbasic.console.constants.MessageConstants.*;

import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.programmers.springbasic.console.ConsoleInputHandler;
import com.programmers.springbasic.console.ConsoleOutputHandler;
import com.programmers.springbasic.console.constants.VoucherCommandType;

@Component
public class VoucherCommandExecutor implements Command {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(VoucherCommandExecutor.class);

	private final ConsoleOutputHandler consoleOutputHandler;
	private final ConsoleInputHandler consoleInputHandler;
	private final ApplicationContext applicationContext;

	public VoucherCommandExecutor(ConsoleOutputHandler consoleOutputHandler, ConsoleInputHandler consoleInputHandler,
		ApplicationContext applicationContext) {
		this.consoleOutputHandler = consoleOutputHandler;
		this.consoleInputHandler = consoleInputHandler;
		this.applicationContext = applicationContext;
	}

	@Override
	public void execute() {
		while (true) {
			try {
				consoleOutputHandler.print(VOUCHER_MENU);
				String userInput = consoleInputHandler.readString();
				VoucherCommandType voucherCommandType = VoucherCommandType.from(userInput);

				if (voucherCommandType == VoucherCommandType.RETURN_TO_MAIN) {
					return;
				}

				Command command = applicationContext.getBean(voucherCommandType.getCommandClass());
				command.execute();
			} catch (IllegalArgumentException e) {
				logger.warn(e.getMessage());
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
	}
}
