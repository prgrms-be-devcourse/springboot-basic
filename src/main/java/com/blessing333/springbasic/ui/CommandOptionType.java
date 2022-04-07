package com.blessing333.springbasic.ui;

import com.blessing333.springbasic.ui.exception.CommandNotSupportedException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
@Getter
public enum CommandOptionType {
    CREATE("create", false, "새로운 바우쳐를 생성합니다."),
    LIST("list", false, "생성된 바우쳐를 조회합니다."),
    QUIT("quit", false, "프로그램을 종료합니다.");

    private static final Map<String, CommandOptionType> availableCommandOptionType = initAvailableCommandOptionList();
    private final String optionName;
    private final boolean argumentRequired;
    private final String description;

    private static Map<String, CommandOptionType> initAvailableCommandOptionList() {
        return Collections.unmodifiableMap(
                Stream.of(values())
                        .collect(Collectors.toMap(CommandOptionType::getOptionName, Function.identity()))
        );
    }

    public static Map<String, CommandOptionType> getAvailableCommandOptionType() {
        return availableCommandOptionType;
    }

    public static CommandOptionType find(String target) throws CommandNotSupportedException {
        CommandOptionType ret = availableCommandOptionType.get(target);
        if (ret == null)
            throw new CommandNotSupportedException();
        else
            return ret;
    }
}
