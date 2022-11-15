package org.programmers.springbootbasic.data;

import org.programmers.springbootbasic.exception.WrongCommandInputException;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum VoucherMainMenuCommand {
    EXIT("exit"), CREATE("create"), LIST("list"), BLACKLIST("blacklist");
    private final String command;

    VoucherMainMenuCommand(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    private static final Map<String, VoucherMainMenuCommand> BY_COMMAND = Stream.of(values())
            .collect(Collectors.toMap(VoucherMainMenuCommand::getCommand, e -> e));

    private static VoucherMainMenuCommand filterNullInput(VoucherMainMenuCommand command) {
        if (command == null) throw new WrongCommandInputException("목록에 없는 입력 커맨드입니다.");
        return command;
    }

    public static VoucherMainMenuCommand valueOfCommand(String command) {
        return filterNullInput(BY_COMMAND.get(command));
    }
}
