package programmers.org.voucher.constant;

import java.util.Arrays;
import java.util.NoSuchElementException;

import static programmers.org.voucher.exception.ErrorMessage.COMMAND_ERROR_MESSAGE;

public enum Command {
    CREATE("create"),
    LIST("list"),
    EXIT("exit");

    private final String type;

    Command(String type) {
        this.type = type;
    }

    public static Command find(String type) {
        return Arrays.stream(Command.values())
                .filter(command -> command.type.equals(type))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException(COMMAND_ERROR_MESSAGE.getMessage()));
    }
}
