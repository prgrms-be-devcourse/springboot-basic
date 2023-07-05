package com.demo.voucher.io;

import com.demo.voucher.exception.CommandInputException;
import com.demo.voucher.io.dto.CommandTypeDescriptionDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum CommandType {
    EXIT("종료", "프로그램을 종료합니다."),
    CREATE("생성", "새로운 바우처를 생성합니다."),
    LIST("목록", "전체 바우처 목록을 조회합니다.");

    private static final Map<String, CommandType> COMMAND_MAP = Collections.unmodifiableMap(Stream.of(values())
            .collect(Collectors.toMap(CommandType::getCommand, Function.identity())));

    private final String command;
    private final String description;

    public static CommandType of(String input) throws CommandInputException {
        if (COMMAND_MAP.containsKey(input)) {
            return COMMAND_MAP.get(input);
        }
        throw new CommandInputException();
    }

    public CommandTypeDescriptionDto getCommandTypeDescription() {
        return new CommandTypeDescriptionDto(command, description);
    }
}
