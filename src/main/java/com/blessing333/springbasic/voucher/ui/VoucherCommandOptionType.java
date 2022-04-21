package com.blessing333.springbasic.voucher.ui;

import com.blessing333.springbasic.common.ui.CommandNotSupportedException;
import com.blessing333.springbasic.common.ui.CommandOptionType;
import lombok.AllArgsConstructor;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
public enum VoucherCommandOptionType implements CommandOptionType {
    CREATE("create", false, "새로운 바우쳐를 생성합니다."),
    LIST("list", false, "생성된 바우쳐를 조회합니다."),
    QUIT("exit", false, "프로그램을 종료합니다.");

    private static final Map<String, CommandOptionType> availableCommandOptionType = initAvailableCommandOptionList();
    private final String optionName;
    private final boolean argumentRequired;
    private final String description;

    private static Map<String, CommandOptionType> initAvailableCommandOptionList() {
        return Collections.unmodifiableMap(
                Stream.of(values())
                        .collect(Collectors.toMap(VoucherCommandOptionType::getOptionName, Function.identity()))
        );
    }

    public static Map<String, CommandOptionType> getAvailableCommandOptionType() {
        return availableCommandOptionType;
    }

    public static VoucherCommandOptionType find(String target) throws CommandNotSupportedException {
        CommandOptionType type = availableCommandOptionType.get(target);
        if (type == null)
            throw new CommandNotSupportedException();
        return (VoucherCommandOptionType) type;
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
