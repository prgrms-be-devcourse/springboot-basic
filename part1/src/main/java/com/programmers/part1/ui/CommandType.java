package com.programmers.part1.ui;

import com.programmers.part1.exception.TypeMissingException;

import java.util.Arrays;

public enum CommandType {

    CUSTOMER_CREATE("1"),
    CUSTOMER_FIND_LIST("2"),
    VOUCHER_CREATE("3"),
    VOUCHER_FIND_LIST("4"),
    ADD_VOUCHER_TO_CUSTOMER("5"),
    LIST_CUSTOMER_BY_VOUCHER("6"),
    DELETE_VOUCHER_FROM_CUSTOMER("7"),
    LIST_VOUCHER_BY_CUSTOMER("8"),
    EXIT("exit");

    private final String command;

    CommandType(String command) {
        this.command = command;
    }

    public static CommandType getCommandType(String command) {
        return Arrays.stream(values())
                .filter(type -> type.command.equals(command))
                .findAny()
                .orElseThrow(() -> new TypeMissingException("올바른 명령이 아닙니다. 다시 입력해주세요."));
    }
}
