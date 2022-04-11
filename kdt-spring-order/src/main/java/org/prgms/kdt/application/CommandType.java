package org.prgms.kdt.application;

import java.util.Arrays;

public enum CommandType {
    EXIT("exit"),
    CREATE("create"),
    LIST("list");

    private final String type;

    CommandType(String type) {
        this.type = type;
    }

    public static CommandType findCommandType (String type) {
        return Arrays.stream(values())
                .filter(i -> i.type.equals(type))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("지원하지 않는 타입 입니다."));
    }
}
