package org.promgrammers.voucher.view;


import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public enum CustomerAction {
    CREATE("create"),
    FIND_ALL("list"),
    FIND_ONE("findOne"),
    UPDATE("update"),
    DELETE("delete"),

    ASSIGN("assign"),

    EXIT("exit");

    private final String action;

    public static CustomerAction fromAction(String action) {

        for (CustomerAction value : CustomerAction.values()) {
            if (value.action.equalsIgnoreCase(action)) {
                return value;
            }
        }
        throw new IllegalArgumentException("유효하지 않은 명령어 타입: " + action);
    }
}