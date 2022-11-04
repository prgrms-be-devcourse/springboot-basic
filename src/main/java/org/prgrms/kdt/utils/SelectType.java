package org.prgrms.kdt.utils;

import java.util.Arrays;

public enum SelectType {
    EXIT("exit"),
    CREATE("create"),
    LIST("list"),
    NOTHING("");

    private final String type;

    SelectType(String type){
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static SelectType findSelectType(String selection){
        return Arrays.stream(SelectType.values())
                .filter(selectType -> selectType.getType().equals(selection))
                .findFirst().orElse(NOTHING);
    }
}
