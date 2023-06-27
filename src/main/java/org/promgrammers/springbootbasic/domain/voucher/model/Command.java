package org.promgrammers.springbootbasic.domain.voucher.model;

import java.util.Arrays;

public enum Command {

    EXIT("1"),
    CREATE("2"),
    LIST("3"),
    BLACKLIST("4");

    private final String commandNumber;

    Command(String commandNumber) {
        this.commandNumber = commandNumber;
    }

    public static Command of(String inputCommandNumber) {
        return Arrays.stream(values())
                .filter(value -> value.commandNumber.equals(inputCommandNumber))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 명령어 입력입니다 => " + inputCommandNumber));
    }
}
