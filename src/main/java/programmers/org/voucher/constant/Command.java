package programmers.org.voucher.constant;

import java.util.Arrays;
import java.util.Optional;

public enum Command {
    CREATE("create"),
    LIST("list"),
    EXIT("exit");

    private final String commandString;

    Command(String commandString) {
        this.commandString = commandString;
    }

    public static Optional<Command> find(String commandString) {
        return Arrays.stream(Command.values())
                .filter(command -> command.commandString.equals(commandString))
                .findAny();
    }
}
