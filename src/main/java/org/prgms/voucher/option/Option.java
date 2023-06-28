package org.prgms.voucher.option;

import lombok.Getter;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum Option {
    CREATE("create", "Type create to create a new voucher."),
    LIST("list", "Type list to list all vouchers."),
    EXIT("exit", "Type exit to exit the program.");

    private static final Map<String, Option> OPTION_MAP =
            Collections.unmodifiableMap(Stream.of(values())
                    .collect(Collectors.toMap(Option::getOption, Function.identity())));

    private final String option;
    private final String info;

    Option(String option, String info) {
        this.option = option;
        this.info = info;
    }

    public static Option find(String option) {
        if (!OPTION_MAP.containsKey(option)) {
            throw new IllegalArgumentException("유효하지 않는 옵셥입니다.");
        }
        return OPTION_MAP.get(option);
    }
}
