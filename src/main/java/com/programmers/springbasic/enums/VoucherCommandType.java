package com.programmers.springbasic.enums;

import static com.programmers.springbasic.enums.ErrorCode.*;

import java.util.Arrays;

import com.programmers.springbasic.command.Command;
import com.programmers.springbasic.command.voucher.CreateVoucherCommand;
import com.programmers.springbasic.command.voucher.DeleteVoucherCommand;
import com.programmers.springbasic.command.voucher.GetAllVouchersCommand;
import com.programmers.springbasic.command.voucher.GetVoucherDetailCommand;
import com.programmers.springbasic.command.voucher.UpdateVoucherCommand;
import com.programmers.springbasic.command.wallet.GetCustomersByVoucher;

public enum VoucherCommandType {
	CREATE_VOUCHER("1", CreateVoucherCommand.class),
	GET_ALL_VOUCHERS("2", GetAllVouchersCommand.class),
	GET_VOUCHER_DETAIL("3", GetVoucherDetailCommand.class),
	UPDATE_VOUCHER("4", UpdateVoucherCommand.class),
	DELETE_VOUCHER("5", DeleteVoucherCommand.class),
	GET_CUSTOMERS_BY_VOUCHER("15", GetCustomersByVoucher.class),
	RETURN_TO_MAIN("return", null);

	private final String inputString;
	private final Class<? extends Command> commandClass;

	VoucherCommandType(String inputString, Class<? extends Command> commandClass) {
		this.inputString = inputString;
		this.commandClass = commandClass;
	}

	public static VoucherCommandType from(String readString) {
		return Arrays.stream(VoucherCommandType.values())
			.filter(type -> type.inputString.equalsIgnoreCase(readString))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException(INVALID_COMMAND_TYPE.getMessage()));
	}

	public Class<? extends Command> getCommandClass() {
		return this.commandClass;
	}
}
