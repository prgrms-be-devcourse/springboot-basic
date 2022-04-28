package com.blessing333.springbasic.console.customer.ui;

import com.blessing333.springbasic.console.ui.CommandNotSupportedException;
import com.blessing333.springbasic.console.ui.CommandOptionType;
import lombok.AllArgsConstructor;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
public enum CustomerCommandOptionType implements CommandOptionType{
    CREATE("create", false, "새로운 고객을 생성합니다."),
    LIST("list", false, "모든 고객의 정보를 조회합니다."),
    INQUIRY("inquiry", false, "고객 ID로 특정 고객 정보를 조회합니다."),
    QUIT("exit", false, "프로그램을 종료합니다.");

    private static final Map<String, CommandOptionType> availableCommandOptionTypes = initAvailableCommandOptionList();
    private final String optionName;
    private final boolean argumentRequired;
    private final String description;

    private static Map<String, CommandOptionType> initAvailableCommandOptionList() {
        return Collections.unmodifiableMap(
                Stream.of(values())
                        .collect(Collectors.toMap(CustomerCommandOptionType::getOptionName, Function.identity()))
        );
    }

    public static Map<String, CommandOptionType> getAvailableCommandOptionTypes() {
        return availableCommandOptionTypes;
    }

    public static CustomerCommandOptionType fromString(String target) throws CommandNotSupportedException {
        CommandOptionType type = availableCommandOptionTypes.get(target);
        if (type == null)
            throw new CommandNotSupportedException();
        return (CustomerCommandOptionType) type;
    }

    @Override
    public String getOptionName() {
        return this.optionName;
    }

    @Override
    public boolean isArgumentRequired() {
        return this.argumentRequired;
    }

    @Override
    public String getDescription() {
        return this.description;
    }
}
