package programmers.org.voucher.constant;

import programmers.org.voucher.exception.ErrorMessage;

import java.util.Arrays;
import java.util.NoSuchElementException;

public enum Command {
    CREATE,
    LIST,
    FIND,
    UPDATE,
    DELETE,
    EXIT;

    public static Command find(String type) {
        return Arrays.stream(Command.values())
                .filter(command -> command.name().equals(type.toUpperCase()))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException(ErrorMessage.COMMAND_ERROR_MESSAGE.getMessage()));
    }
}
