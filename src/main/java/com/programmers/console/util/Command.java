package com.programmers.console.util;

public enum Command {
    LIST,
    CREATE,
    EXIT;

    private static final String NOT_FOUND_MESSAGE = "[ERROR] 해당 명령어를 찾을 수 없습니다.\n";

    public static Command of(String type) {
        try {
            return valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(NOT_FOUND_MESSAGE);
        }
    }
}
