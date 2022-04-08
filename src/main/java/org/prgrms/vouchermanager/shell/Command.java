package org.prgrms.vouchermanager.shell;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * COMMAND를 Enum 타입으로 관리
 */
public enum Command {
    INVALID_COMMAND,
    EXIT,
    CREATE,
    LIST;

    public static Optional<Command> findCommand(String command) {
        return Stream.of(values())
                .filter(v -> command.equalsIgnoreCase(v.toString()))
                .findFirst();
    }
}
