package programmers.org.voucher.constant;

import java.util.Arrays;
import java.util.Optional;

public enum Command {
    CREATE,
    LIST,
    EXIT;

    public static Optional<Command> find(String type) {
        return Arrays.stream(Command.values())
                .filter(command -> command.name().equals(type))
                .findAny();
    }
}
