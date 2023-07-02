package programmers.org.voucher.constant;

import java.util.Arrays;
import java.util.Optional;

public enum Command {
    CREATE("create"),
    LIST("list"),
    EXIT("exit");

    private final String type;

    Command(String type) {
        this.type = type;
    }

    public static Optional<Command> find(String type) {
        return Arrays.stream(Command.values())
                .filter(command -> command.type.equals(type))
                .findAny();
    }
}
