package com.programmers.voucher.global.io.command;

import java.util.Arrays;
import java.util.Objects;

public enum CustomerCommandType implements CommandType {
    CREATE("create"),
    LIST("list"),
    UPDATE("update"),
    DELETE("delete"),
    BLACKLIST("blacklist");

    private final String type;

    CustomerCommandType(String type) {
        this.type = type;
    }

    public static CustomerCommandType getValue(String type) {
        return Arrays.stream(values())
                .filter(t -> Objects.equals(t.type, type))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("못 찾겠다."));
    }

    @Override
    public String getType() {
        return type;
    }
}
