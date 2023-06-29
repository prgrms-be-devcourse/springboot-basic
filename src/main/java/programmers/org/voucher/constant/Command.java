package programmers.org.voucher.constant;

import java.util.Arrays;
import java.util.NoSuchElementException;

import static programmers.org.voucher.exception.ErrorMessage.COMMAND_ERROR_MESSAGE;

public enum Command {
    CREATE("create"),
    LIST("list"),
    EXIT("exit");

    private final String content;

    Command(String commandString) {
        this.content = commandString;
    }

    public static Command find(String content) {
        return Arrays.stream(Command.values())
                .filter(command -> command.content.equals(content))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException(COMMAND_ERROR_MESSAGE.getMessage()));
    }
}
