package com.example.demo.util;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum CommandType {

    CREATE_VOUCHER(1),
    PRINT_VOUCHER_LIST(2),
    CREATE_CUSTOMER(3),
    PRINT_CUSTOMER_LIST(4),
    UPDATE_CUSTOMER(5),
    DELETE_CUSTOMER(6),
    EXIT(7);


    private final int commandNum;

    private static final Map<Integer, CommandType> COMMAND_TYPE_MAP =
            Collections.unmodifiableMap(Stream.of(values())
                    .collect(Collectors.toMap(CommandType::getCommandNum, Function.identity())));

    CommandType(int commandNum) {
        this.commandNum = commandNum;
    }

    public int getCommandNum() {
        return commandNum;
    }

    public static CommandType from(Integer input) {
        if (COMMAND_TYPE_MAP.containsKey(input)) {
            return COMMAND_TYPE_MAP.get(input);
        }
        throw new IllegalArgumentException("입력하신 " + input + "는 유효한 커멘드가 아닙니다. \n 1 ~ 7 사이 숫자를 입력하세요.\n");
    }
}
