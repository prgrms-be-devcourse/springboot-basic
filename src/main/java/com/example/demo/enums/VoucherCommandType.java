package com.example.demo.enums;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum VoucherCommandType {
    
    CREATE_VOUCHER(1),
    PRINT_VOUCHER_LIST(2),
    UPDATE_VOUCHER_AMOUNT(3),
    DELETE_VOUCHER(4);

    private final int commandNum;

    private static final Map<Integer, VoucherCommandType> COMMAND_TYPE_MAP =
            Collections.unmodifiableMap(Stream.of(values())
                    .collect(Collectors.toMap(VoucherCommandType::getCommandNum, Function.identity())));

    VoucherCommandType(int commandNum) {
        this.commandNum = commandNum;
    }

    public int getCommandNum() {
        return commandNum;
    }

    public static VoucherCommandType from(Integer input) {
        if (COMMAND_TYPE_MAP.containsKey(input)) {
            return COMMAND_TYPE_MAP.get(input);
        }
        throw new IllegalArgumentException("입력하신 " + input + "는 유효한 커멘드가 아닙니다. \n 1 ~ 4 사이 숫자를 입력하세요.\n");
    }

}
