package com.prgrms.management.command;

import java.util.Arrays;
import java.util.NoSuchElementException;

public enum Command {
    CREATE,LIST,BLACKLIST,EXIT;

    public static Command of(String input) {
        return Arrays.stream(Command.values())
                .filter(e -> e.name().equals(input.toUpperCase()))
                .findAny()
                .orElseThrow(()-> new NoSuchElementException("없는 명령어입니다."));
    }
}
