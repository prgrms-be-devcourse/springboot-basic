package com.programmers.springbasic.constants;

import static com.programmers.springbasic.constants.ErrorCode.*;

import java.util.Arrays;

import com.programmers.springbasic.command.Command;
import com.programmers.springbasic.command.customer.CreateCustomerCommand;
import com.programmers.springbasic.command.customer.RemoveCustomerCommand;
import com.programmers.springbasic.command.customer.GetAllCustomersCommand;
import com.programmers.springbasic.command.customer.GetBlacklistCustomersCommand;
import com.programmers.springbasic.command.customer.GetCustomerDetailCommand;
import com.programmers.springbasic.command.customer.UpdateCustomerCommand;
import com.programmers.springbasic.command.wallet.AssignVoucherToCustomerCommand;
import com.programmers.springbasic.command.wallet.GetCustomerVouchersCommand;
import com.programmers.springbasic.command.wallet.RemoveCustomerVoucherCommand;

public enum CustomerCommandType {
	CREATE_CUSTOMER("1", CreateCustomerCommand.class),
	GET_ALL_CUSTOMERS("2", GetAllCustomersCommand.class),
	GET_CUSTOMER_DETAIL("3", GetCustomerDetailCommand.class),
	UPDATE_CUSTOMER("4", UpdateCustomerCommand.class),
	DELETE_CUSTOMER("5", RemoveCustomerCommand.class),
	GET_BLACKLIST_CUSTOMERS("6", GetBlacklistCustomersCommand.class),
	ASSIGN_VOUCHER_TO_CUSTOMER("7", AssignVoucherToCustomerCommand.class),
	GET_CUSTOMER_VOUCHERS("8", GetCustomerVouchersCommand.class),
	REMOVE_CUSTOMER_VOUCHER("9", RemoveCustomerVoucherCommand.class),
	RETURN_TO_MAIN("0", null);

	private final String inputString;
	private final Class<? extends Command> commandClass;

	CustomerCommandType(String inputString, Class<? extends Command> commandClass) {
		this.inputString = inputString;
		this.commandClass = commandClass;
	}

	public static CustomerCommandType from(String readString) {
		return Arrays.stream(CustomerCommandType.values())
			.filter(type -> type.inputString.equalsIgnoreCase(readString))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException(INVALID_COMMAND_TYPE.getMessage()));
	}

	public Class<? extends Command> getCommandClass() {
		return this.commandClass;
	}
}
