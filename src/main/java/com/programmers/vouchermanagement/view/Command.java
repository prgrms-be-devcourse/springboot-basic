package com.programmers.vouchermanagement.view;

import lombok.Getter;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum Command {

    EXIT("1", "프로그램 종료"),
    CREATE("2", "바우처 생성"),
    LIST("3", "바우처 조회"),
    DELETE("4", "바우처 삭제"),
    UPDATE("5", "바우처 수정");

    private final String number;
    private final String description;
    private static final Map<String, Command> COMMAND_MAP;

    static {
        COMMAND_MAP = Collections.unmodifiableMap(Stream.of(values())
                .collect(Collectors.toMap(Command::getNumber, Function.identity())));
    }

    Command(String number, String description) {
        this.number = number;
        this.description = description;
    }

    public static Command from(String number) {
        if (COMMAND_MAP.containsKey(number)) {
            return COMMAND_MAP.get(number);
        }
        throw new IllegalArgumentException("존재하지 않는 명령어 입니다.");
    }
}
