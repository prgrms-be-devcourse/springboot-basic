package programmers.org.voucher.constant;

import java.util.Arrays;
import java.util.NoSuchElementException;

import static programmers.org.voucher.exception.ErrorMessage.INVALID_COMMAND;

public enum Command {
    CREATE,
    LIST,
    FIND,
    UPDATE,
    DELETE,
    EXIT
    ;

    public static Command find(String type) {
        return Arrays.stream(Command.values())
                .filter(command -> command.name().equalsIgnoreCase(type))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException(INVALID_COMMAND.getMessage()));
    }
}
