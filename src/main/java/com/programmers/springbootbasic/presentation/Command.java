package com.programmers.springbootbasic.presentation;

import java.util.Arrays;

public enum Command {
    CREATE("생성"),
    LIST("조회"),
    EXIT("나가기");
    private static final String INVALID_COMMAND = "잘못된 명령어 입니다. 현재 입력 값: ";

    private final String description;

    Command(String description) {
        this.description = description;
    }

    public static Command from(String inputCommand) {
        return Arrays.stream(values())
                .filter(enumerate -> inputCommand.equals(enumerate.getDescription()))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(INVALID_COMMAND + inputCommand));
    }

    public String getDescription() {
        return description;
    }
}
