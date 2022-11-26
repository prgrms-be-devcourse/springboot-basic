package com.program.commandLine.io;

import java.util.Arrays;
import java.util.Objects;

public enum Recheck {
    Y(true),N(false);

    private final Boolean result;
    Recheck(boolean result) {
        this.result = result;
    }
    public static Boolean get(String input){
        Recheck recheck = Arrays.stream(values())
                .filter(type -> Objects.equals(input.toUpperCase(),type.toString()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("! 잘못된 입력입니다."));
        return recheck.result;
    }

}
