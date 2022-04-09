package org.prgms.voucheradmin.domain.console;

import org.prgms.voucheradmin.domain.voucher.exception.WrongInputException;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Command {
    EXIT("exit"), CREATE("create"), LIST("list"), BLACKLIST("blacklist");

    private final String command;
    private static final Map<String, Command> commands =
            Collections.unmodifiableMap(Stream.of(values())
                    .collect(Collectors.toMap(Command::getCommand, Function.identity())));

    Command(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    public static Command findCommand(String command) throws WrongInputException {
        return Optional.ofNullable(commands.get(command)).orElseThrow(WrongInputException::new);
    }
}
