package com.devmission.springbootbasic;

import java.util.Arrays;

public enum Command {

    CREATE,
    LIST,
    EXIT;

    public static Command from(String name) {
        return Arrays.stream(values()).filter(command -> command.name().equals(name.toUpperCase()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("선택할 수 없는 명령입니다."));
    }

}
