package org.prgrms.kdt.application.cmd;

import org.prgrms.kdt.common.exception.ExceptionMessage;
import org.prgrms.kdt.application.cmd.exception.InvalidCommandException;

import java.util.Arrays;

public enum Command {
    CREATE_VOUCHER("create_voucher"),
    LIST_VOUCHER("list_voucher"),
    LIST_CUSTOMER("list_customer"),
    EXIT("exit");

    private final String command;

    Command(String command) {
        this.command = command;
    }

    public static Command findByCommandName(String commandName){
        return Arrays.stream(Command.values())
                .filter(cmd -> cmd.command.equals(commandName))
                .findAny()
                .orElseThrow(()->new InvalidCommandException(ExceptionMessage.INVALID_COMMAND.getMessage()));
    }
}
