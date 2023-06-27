package org.promgrammers.springbootbasic.domain.voucher.model;

import java.util.Arrays;

public enum Command {

    EXIT("exit","1"),
    CREATE("create","2"),
    LIST("list","3"),
    BLACKLIST("blacklist","4");

    private final String command;
    private final String commandNumber;

    Command(String command, String commandNumber) {
        this.command = command;
        this.commandNumber = commandNumber;
    }

    public static Command of(String inputCommandNumber) {
        return Arrays.stream(values())
                .filter(value -> value.commandNumber.equals(inputCommandNumber))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 명령어 입력입니다 => " + inputCommandNumber));
    }
}
