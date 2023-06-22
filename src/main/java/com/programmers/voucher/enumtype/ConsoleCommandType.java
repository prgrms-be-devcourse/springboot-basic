package com.programmers.voucher.enumtype;

import java.util.Arrays;
import java.util.Objects;

public enum ConsoleCommandType {
    CREATE("create"),
    LIST("list"),
    EXIT("exit");

    private final String input;

    ConsoleCommandType(String input) {
        this.input = input;
    }

    public static ConsoleCommandType getValue(String input) {
        return Arrays.stream(values())
                .filter(i -> Objects.equals(i.input, input))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 명령어입니다. 현재 입력: " + input));
    }
}
