package org.programmers.kdt.weekly.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.programmers.kdt.weekly.command.io.Console;
import org.programmers.kdt.weekly.command.io.InfoMessageType;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class StartCommandLine implements Runnable {

	private final Console console;
	private final CustomerCommandLine customerCommand;
	private final VoucherCommandLine voucherCommandLine;

	public StartCommandLine(
		Console console,
		CustomerCommandLine customerCommandLine,
		VoucherCommandLine voucherCommandLine) {
		this.console = console;
		this.customerCommand = customerCommandLine;
		this.voucherCommandLine = voucherCommandLine;
	}

	@Override
	public void run() {
		StartCommandType startCommandType = StartCommandType.DEFAULT;

		while (startCommandType.isRunnable()) {
			this.console.printCommandDescription(getCommandDescription());
			var userInput = this.console.getUserInput();

			try {
				startCommandType = StartCommandType.of(userInput);

				switch (startCommandType) {
					case VOUCHER -> this.voucherCommandLine.run();
					case CUSTOMER -> this.customerCommand.run();
					case EXIT -> this.console.programExitMessage();
				}
			} catch (IllegalArgumentException e) {
				log.error("startCommandLine error", e);
				this.console.printInfoMessage(InfoMessageType.COMMAND);
			}
		}
	}

	private List<String> getCommandDescription() {
		List<String> commandDescription = new ArrayList<>();
		Arrays.stream(StartCommandType.values())
			.forEach((v) -> commandDescription.add(v.getCommandMessage()));

		return commandDescription;
	}
}