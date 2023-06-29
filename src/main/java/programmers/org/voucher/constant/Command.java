package programmers.org.voucher.constant;

import java.util.Arrays;
import java.util.NoSuchElementException;

import static programmers.org.voucher.exception.ErrorMessage.COMMAND_ERROR_MESSAGE;

public enum Command {
    CREATE("create"),
    LIST("list"),
    EXIT("exit");

    private final String commandString;

    Command(String commandString) {
        this.commandString = commandString;
    }

    public static Command find(String commandString) {
        return Arrays.stream(Command.values())
                .filter(command -> command.commandString.equals(commandString))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException(COMMAND_ERROR_MESSAGE.getMessage()));
    }
}
