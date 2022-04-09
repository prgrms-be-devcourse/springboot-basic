package org.prgms.voucheradmin.domain.console;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.prgms.voucheradmin.global.exception.WrongInputException;

public enum Commands {
    EXIT("exit"), CREATE("create"), LIST("list"), BLACKLIST("blacklist");

    private final String command;
    private static final Map<String, Commands> commands =
            Collections.unmodifiableMap(Stream.of(values())
                    .collect(Collectors.toMap(Commands::getCommand, Function.identity())));

    Commands(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    public static Commands findCommand(String command) throws WrongInputException {
        return Optional.ofNullable(commands.get(command)).orElseThrow(WrongInputException::new);
    }
}
