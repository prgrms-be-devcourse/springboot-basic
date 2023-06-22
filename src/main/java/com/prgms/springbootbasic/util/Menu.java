package com.prgms.springbootbasic.util;

import com.prgms.springbootbasic.exception.NoSuchMenuException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Menu {
	
	EXIT("exit"),
	CREATE("create"),
	LIST("list");
	
	private static final String ERROR_NO_SUCH_MENU = "[Error] Can't Find Such Menu";
	private static final List<Menu> MENU = Arrays.stream(Menu.values()).collect(Collectors.toList());
	private final String command;
	
	Menu(String command) {
		this.command = command;
	}
	
	public static Menu of(String command) {
		return MENU.stream()
			       .filter(m -> m.command.equals(command))
			       .findFirst()
			       .orElseThrow(() -> new NoSuchMenuException(ERROR_NO_SUCH_MENU));
	}
	
}
