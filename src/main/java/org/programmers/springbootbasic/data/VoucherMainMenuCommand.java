package org.programmers.springbootbasic.data;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum VoucherMainMenuCommand {
    EXIT("exit"), CREATE("create"), LIST("list"), WRONG_INPUT("wrong");
    final private String command;

    VoucherMainMenuCommand(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    private static final Map<String, VoucherMainMenuCommand> BY_COMMAND = Stream.of(values())
            .collect(Collectors.toMap(VoucherMainMenuCommand::getCommand, e -> e));

    private static VoucherMainMenuCommand filterNullInput(Optional<VoucherMainMenuCommand> optionalCommand) {
        if(optionalCommand.isEmpty()) return VoucherMainMenuCommand.WRONG_INPUT;
        else return optionalCommand.get();
    }

    public static VoucherMainMenuCommand valueOfCommand(String command) {
        return filterNullInput(Optional.ofNullable(BY_COMMAND.get(command)));
    }

}
