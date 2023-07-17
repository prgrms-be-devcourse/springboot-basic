package com.example.demo.enums;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum IndexMenuCommand {
    CUSTOMER(1),
    VOUCHER(2),
    EXIT(3);

    private final int commandNum;

    private static final Map<Integer, IndexMenuCommand> COMMAND_TYPE_MAP =
            Collections.unmodifiableMap(Stream.of(values())
                    .collect(Collectors.toMap(IndexMenuCommand::getCommandNum, Function.identity())));

    IndexMenuCommand(int commandNum) {
        this.commandNum = commandNum;
    }

    public int getCommandNum() {
        return commandNum;
    }

    public static IndexMenuCommand from(Integer input) {
        if (COMMAND_TYPE_MAP.containsKey(input)) {
            return COMMAND_TYPE_MAP.get(input);
        }
        throw new IllegalArgumentException("입력하신 " + input + "는 유효한 커멘드가 아닙니다. \n 1 ~ 3 사이의 숫자를 입력하세요.\n");
    }

}
