package org.prgrms.kdt;

import java.util.Optional;

public enum CommandType {
    EXIT("종료"),
    CREATE("바우처 생성"),
    LIST("바우처 목록"),
    BLACK("블랙리스트");

    private String type;

    CommandType(String type) {
        this.type = type;
    }

    public static Optional<CommandType> matchCommandType(String input) {
        for (CommandType commandType : CommandType.values()) {
            if (commandType.toString().equalsIgnoreCase(input)) return Optional.of(commandType);
        }
        return Optional.empty();
    }
}
