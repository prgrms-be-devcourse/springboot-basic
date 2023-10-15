package com.programmers.springbasic.console;

import java.util.Scanner;

import org.springframework.stereotype.Component;

import com.programmers.springbasic.entity.MenuType;

@Component
public class ConsoleInputHandler {
	private final Scanner scanner = new Scanner(System.in);

	public String readString() {
		return scanner.nextLine();
	}

	public long readLong() {
		return Long.parseLong(scanner.nextLine());
	}

	public MenuType readMenu() {
		return MenuType.from(readLine());
	}

	private String readLine() {
		return scanner.nextLine();
	}

}
