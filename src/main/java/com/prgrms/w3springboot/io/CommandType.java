package com.prgrms.w3springboot.io;

import java.util.Arrays;
import java.util.function.Supplier;

import com.prgrms.w3springboot.io.strategy.CommandStrategy;
import com.prgrms.w3springboot.io.strategy.CreateCommand;
import com.prgrms.w3springboot.io.strategy.DeleteCommand;
import com.prgrms.w3springboot.io.strategy.ExitCommand;
import com.prgrms.w3springboot.io.strategy.ListCommand;
import com.prgrms.w3springboot.io.strategy.UpdateCommand;
import com.prgrms.w3springboot.voucher.service.VoucherService;

public enum CommandType {
	CREATE("create", CreateCommand::new),
	LIST("list", ListCommand::new),
	EXIT("exit", ExitCommand::new),
	UPDATE("update", UpdateCommand::new),
	DELETE("delete", DeleteCommand::new);

	private final String command;
	private final Supplier<CommandStrategy> supplier;

	CommandType(String command, Supplier<CommandStrategy> supplier) {
		this.command = command;
		this.supplier = supplier;
	}

	public static CommandType of(final String commandType) {
		return Arrays.stream(CommandType.values())
			.filter(command -> command.isCommandType(commandType))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("잘못된 타입을 입력받았습니다."));
	}

	private boolean isCommandType(final String commandType) {
		return this.command.equals(commandType);
	}

	public boolean execute(Input input, Output output, VoucherService voucherService) {
		return this.supplier.get().execute(input, output, voucherService);
	}

}