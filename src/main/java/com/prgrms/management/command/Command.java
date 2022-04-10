package com.prgrms.management.command;

import java.util.Arrays;
import java.util.Locale;
import java.util.NoSuchElementException;

public enum Command {
    CREATE,LIST,EXIT;

    public static Command parse(String input) {
        return Arrays.stream(Command.values())
                .filter(e -> e.name().equals(input.toUpperCase()))
                .findAny()
                .orElseThrow(()-> new NoSuchElementException("잘못된 명령어입니다."));
    }
}
