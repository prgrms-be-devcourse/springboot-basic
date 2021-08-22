package org.prgrms.kdt.application.voucher.type;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum CommandType {
    EXIT,
    CREATE,
    LIST;

    private static final Map<Integer, CommandType> ORDINAL_TO_ENUM =
            Collections.unmodifiableMap(Stream.of(values())
                    .collect(Collectors.toMap(Enum::ordinal, Function.identity())));

    private static final String LIST_OF_ENUM =
            ORDINAL_TO_ENUM.entrySet()
                    .stream()
                    .map(entry -> entry.getKey() + ": " + entry.getValue())
                    .collect(Collectors.joining("\n"));


    public static Optional<CommandType> fromOrdinal(int ordinal) {
        CommandType commandType = ORDINAL_TO_ENUM.get(ordinal);
        return Optional.ofNullable(commandType);
    }

    public static String getListOfEnum() {
        return LIST_OF_ENUM;
    }
}
