package com.programmers.voucher.controller;

import java.util.Arrays;

import com.programmers.voucher.exception.WrongCommandException;

public enum Command {

	CREATE("create"),
	LIST("list"),
	EXIT("exit");

	private String command;

	Command(String command) {
		this.command = command;
	}

	public static Command getCommand(String chosenCommand) {
		return Arrays.stream(Command.values())
			.filter(i -> i.command.equals(chosenCommand))
			.findFirst()
			.orElseThrow(() -> new WrongCommandException());
	}
}
