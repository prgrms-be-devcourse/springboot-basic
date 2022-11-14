package org.prgrms.kdt.utils;

import org.prgrms.kdt.exceptions.InvalidITypeInputException;

import java.util.Arrays;

import static org.prgrms.kdt.io.IOManager.getSelectWrongMessage;


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
                    throw new InvalidITypeInputException(getSelectWrongMessage());
                });
    }
}
