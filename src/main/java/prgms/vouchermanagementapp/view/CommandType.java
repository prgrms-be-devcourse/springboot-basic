package prgms.vouchermanagementapp.view;

import prgms.vouchermanagementapp.exception.IllegalCommandException;

import java.util.Optional;

public enum CommandType {

    EXIT, CREATE, LIST, BLACKLIST;

    public static Optional<CommandType> of(String command) {
        try {
            return Optional.of(CommandType.valueOf(command.toUpperCase()));
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new IllegalCommandException(command, illegalArgumentException);
        }
    }
}
