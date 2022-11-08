package com.programmers.voucher.controller;

import java.util.Arrays;

import com.programmers.voucher.exception.WrongCommandException;

public enum Command {

	CREATE("create"),
	LIST("list"),
	BLACKLIST("blacklist"),
	EXIT("exit");

	private String option;

	Command(String option) {
		this.option = option;
	}

	public static Command getCommand(String chosenCommand) {
		return Arrays.stream(Command.values())
			.filter(command -> command.option.equals(chosenCommand))
			.findFirst()
			.orElseThrow(() -> new WrongCommandException());
	}
}
