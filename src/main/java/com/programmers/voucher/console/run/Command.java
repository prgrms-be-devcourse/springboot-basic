package com.programmers.voucher.console.run;

import static com.programmers.voucher.core.exception.ExceptionMessage.*;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum Command {

	CREATE_VOUCHER("create"),
	REGISTER_CUSTOMER("register"),
	ALLOCATE_VOUCHER("allocate"),
	REMOVE_VOUCHER("remove"),
	GET_VOUCHERS_BY_CUSTOMER("vouchers_by_customer"),
	GET_CUSTOMERS_BY_VOUCHER("customers_by_voucher"),
	GET_ALL_VOUCHER("vouchers"),
	GET_ALL_CUSTOMER("customers"),
	GET_ALL_BLACKLIST("blacklists"),
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
				log.error(WRONG_COMMAND.getMessage());
				throw new IllegalArgumentException(WRONG_COMMAND.getMessage());
			});
	}
}
