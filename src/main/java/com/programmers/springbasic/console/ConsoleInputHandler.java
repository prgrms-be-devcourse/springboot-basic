package com.programmers.springbasic.console;

import java.util.Scanner;

import org.springframework.stereotype.Component;

import com.programmers.springbasic.ErrorCode;

@Component
public class ConsoleInputHandler {
	private final Scanner scanner = new Scanner(System.in);

	public String readString() {
		String value = scanner.nextLine().trim();
		if(value.isEmpty())
			throw new IllegalArgumentException(ErrorCode.EMPTY_STRING.getMessage());
		return value;
	}

	public long readLong() {
		long value;
		try {
			value = Long.parseLong(scanner.nextLine().trim());
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(ErrorCode.INVALID_NUMBER.getMessage());
		}
		return value;
	}
}
