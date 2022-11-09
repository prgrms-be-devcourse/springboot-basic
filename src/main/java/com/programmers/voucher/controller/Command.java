package com.programmers.voucher.controller;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.programmers.voucher.exception.ExceptionMessage;

public enum Command {

	CREATE("create"),
	LIST("list"),
	BLACKLIST("blacklist"),
	EXIT("exit");

	private static final Logger log = LoggerFactory.getLogger(Command.class);
	private final String option;

	Command(String option) {
		this.option = option;
	}

	public static Command getCommand(String chosenCommand) {
		return Arrays.stream(Command.values())
			.filter(command -> command.option.equals(chosenCommand))
			.findFirst()
			.orElseThrow(() -> {
				log.error(ExceptionMessage.WRONG_COMMAND.getMessage());
				throw new IllegalArgumentException(ExceptionMessage.WRONG_COMMAND.getMessage());
			});
	}
}
