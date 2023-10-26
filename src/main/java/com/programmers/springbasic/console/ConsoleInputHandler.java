package com.programmers.springbasic.console;

import java.util.Scanner;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.programmers.springbasic.enums.ErrorCode;

@Component
public class ConsoleInputHandler {

	private final Scanner scanner = new Scanner(System.in);

	public String readString() {
		String value = scanner.nextLine().strip();
		if (value.isEmpty())
			throw new IllegalArgumentException(ErrorCode.EMPTY_STRING.getMessage());
		return value;
	}

	public long readLong() {
		try {
			return Long.parseLong(scanner.nextLine().strip());
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(ErrorCode.INVALID_NUMBER.getMessage());
		}
	}

	public UUID readUUID() {
		try {
			return UUID.fromString(scanner.nextLine().strip());
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(ErrorCode.INVALID_UUID.getMessage());
		}
	}

}
