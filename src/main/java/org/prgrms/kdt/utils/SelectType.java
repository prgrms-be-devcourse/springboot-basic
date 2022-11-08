package org.prgrms.kdt.utils;

import org.prgrms.kdt.exceptions.WrongSelectException;

import java.util.Arrays;


public enum SelectType {
    EXIT("exit"),
    CREATE("create"),
    LIST("list");

    private final String type;

    SelectType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static SelectType findSelectType(String selection) {
        return Arrays.stream(SelectType.values())
                .filter(selectType -> selectType.getType().equals(selection))
                .findFirst()
                .orElseThrow(() -> {
                    throw new WrongSelectException(MessageType.SELECT_WRONG.getMessage());
                });
    }
}
