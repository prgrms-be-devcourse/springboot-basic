package com.programmers.springbasic.enums;

import static com.programmers.springbasic.enums.ErrorCode.*;

import java.util.Arrays;

import com.programmers.springbasic.command.Command;
import com.programmers.springbasic.command.main.ExitCommand;
import com.programmers.springbasic.command.customer.CreateCustomerCommand;
import com.programmers.springbasic.command.customer.DeleteCustomerCommand;
import com.programmers.springbasic.command.customer.GetAllCustomersCommand;
import com.programmers.springbasic.command.customer.GetBlacklistCustomersCommand;
import com.programmers.springbasic.command.customer.GetCustomerDetailCommand;
import com.programmers.springbasic.command.customer.UpdateCustomerCommand;
import com.programmers.springbasic.command.main.SelectCustomerCommand;
import com.programmers.springbasic.command.main.SelectVoucherCommand;
import com.programmers.springbasic.command.voucher.CreateVoucherCommand;
import com.programmers.springbasic.command.voucher.DeleteVoucherCommand;
import com.programmers.springbasic.command.voucher.GetAllVouchersCommand;
import com.programmers.springbasic.command.voucher.GetVoucherDetailCommand;
import com.programmers.springbasic.command.voucher.UpdateVoucherCommand;
import com.programmers.springbasic.command.wallet.AssignVoucherToCustomerCommand;
import com.programmers.springbasic.command.wallet.GetCustomerVouchersCommand;
import com.programmers.springbasic.command.wallet.GetCustomersByVoucher;
import com.programmers.springbasic.command.wallet.RemoveCustomerVoucherCommand;

public enum MainCommandType {

	VOUCHER_MANAGEMENT("voucher", SelectVoucherCommand.class),
	CUSTOMER_MANAGEMENT("customer", SelectCustomerCommand.class),
	EXIT("exit", ExitCommand.class);

	private final String inputString;
	private final Class<? extends Command> commandClass;

	MainCommandType(String inputString, Class<? extends Command> commandClass) {
		this.inputString = inputString;
		this.commandClass = commandClass;
	}

	public Class<? extends Command> getCommandClass() {
		return this.commandClass;
	}

	public static MainCommandType from(String readString) {
		return Arrays.stream(MainCommandType.values())
			.filter(type -> type.inputString.equalsIgnoreCase(readString))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException(INVALID_COMMAND_TYPE.getMessage()));
	}
}
