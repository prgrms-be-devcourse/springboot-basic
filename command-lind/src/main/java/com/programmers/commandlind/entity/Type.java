package com.programmers.commandlind.entity;

import java.awt.*;
import java.util.Arrays;

public enum Type {
    EXIT(1),
    CREATE(2),
    LIST(3),
    ERROR(-1);

    private static final int BAD_REQUEST = -1;

    private final int code;

    Type(int code) {
        this.code = code;
    }

    public static Type selectMenu(String input) {
        int code = toCode(input);
        return Arrays.stream(Type.values())
                .filter(type -> type.code == code)
                .findFirst()
                .orElse(ERROR);
    }

    private static int toCode(String num) {
        try {
            return Integer.parseInt(num);
        } catch (NumberFormatException e) {
            return BAD_REQUEST;
        }
    }
}

