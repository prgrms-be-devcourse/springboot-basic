package com.programmers.springbasic.constants;

import static com.programmers.springbasic.constants.ErrorCode.*;

import java.util.Arrays;

import com.programmers.springbasic.command.Command;
import com.programmers.springbasic.command.customer.CreateCustomerCommand;
import com.programmers.springbasic.command.customer.DeleteCustomerCommand;
import com.programmers.springbasic.command.customer.GetAllCustomersCommand;
import com.programmers.springbasic.command.customer.GetBlacklistCustomersCommand;
import com.programmers.springbasic.command.customer.GetCustomerDetailCommand;
import com.programmers.springbasic.command.customer.UpdateCustomerCommand;
import com.programmers.springbasic.command.wallet.AssignVoucherToCustomerCommand;
import com.programmers.springbasic.command.wallet.GetCustomerVouchersCommand;
import com.programmers.springbasic.command.wallet.RemoveCustomerVoucherCommand;

public enum CustomerCommandType {
	CREATE_CUSTOMER("6", CreateCustomerCommand.class),
	GET_ALL_CUSTOMERS("7", GetAllCustomersCommand.class),
	GET_BLACKLIST_CUSTOMERS("8", GetBlacklistCustomersCommand.class),
	GET_CUSTOMER_DETAIL("9", GetCustomerDetailCommand.class),
	UPDATE_CUSTOMER("10", UpdateCustomerCommand.class),
	DELETE_CUSTOMER("11", DeleteCustomerCommand.class),
	ASSIGN_VOUCHER_TO_CUSTOMER("12", AssignVoucherToCustomerCommand.class),
	GET_CUSTOMER_VOUCHERS("13", GetCustomerVouchersCommand.class),
	REMOVE_CUSTOMER_VOUCHER("14", RemoveCustomerVoucherCommand.class),
	RETURN_TO_MAIN("return", null);

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
