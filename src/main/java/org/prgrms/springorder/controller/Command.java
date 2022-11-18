package org.prgrms.springorder.controller;

import java.util.Arrays;

import org.prgrms.springorder.domain.ErrorMessage;
import org.prgrms.springorder.exception.NoSuchCommandException;

public enum Command {
	LIST("list"),
	CREATE("create"),
	JOIN("join"),
	EXIT("exit"),
	BLACK_LIST("blacklist"),
	ALLOCATE("allocate"),
	GET("get"),
	DELETE("delete"),
	SEARCH("search");



	private final String orderContext;

	Command(String orderContext) {
		this.orderContext = orderContext;
	}

	public static Command getOrder(String s) {
		return Arrays.stream(Command.values())
			.filter(a -> a.orderContext.equals(s))
			.findAny()
			.orElseThrow(() -> new NoSuchCommandException(ErrorMessage.NO_SUCH_COMMAND_MESSAGE));
	}
}
