package org.programmers.kdt.command;

import org.programmers.kdt.io.Input;
import org.programmers.kdt.io.Output;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

public enum Command {
    // TODO : 여기서 Action 까지 처리해버리도록 수정?
    CREATE("create"), CUSTOMER("customer"), LIST("list"), EXIT("exit"),
    REGISTER("register"), DEREGISTER("deregister"), FIND_CUSTOMER_BY_VOUCHER("find"); // for Customer-Voucher Managing Application

    private final String command;

    Command(String command) {
        this.command = command;
    }


    public static Command of(String command) {
        return Arrays.stream(values())
                .filter(iter -> iter.isEqualTo(command))//iter.getCommand().equals(command.toLowerCase()))
                .findAny()
                .orElseThrow(
                        () -> new RuntimeException(MessageFormat.format("Invalid Command : {0}", command))
                );
    }

    public String getCommand() {
        return command;
    }

    public static List<Command> getVoucherApplicationCommand() {
        return List.of(EXIT, CREATE, LIST, CUSTOMER);
    }

    public static List<Command> getCustomerVoucherManagingApplicationCommand() {
        return List.of(EXIT, LIST, REGISTER, DEREGISTER, FIND_CUSTOMER_BY_VOUCHER);
    }

    public static Command getCommandFromInput(String message, List<Command> validCommand, Input input, Output output) {
        Command command;
        try {
            command = Command.of(input.input(message));
        } catch (RuntimeException e) {
            output.inputError(MessageFormat.format("Invalid Command.\nValid Commands -> {0}", validCommand));
            return null;
        }
        return command;
    }

    public boolean isEqualTo(String command) {
        return this.command.equals(command.toLowerCase());
    }
}
