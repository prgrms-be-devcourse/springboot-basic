package org.prgms.voucher.option;

import lombok.Getter;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum Option {
    CREATE("create"),
    LIST("list"),
    EXIT("exit");

    private static final Map<String, Option> OPTION_MAP =
            Collections.unmodifiableMap(Stream.of(values())
                    .collect(Collectors.toMap(Option::getOption, Function.identity())));

    private final String option;

    Option(String option) {
        this.option = option;
    }

    public static Option find(String option) {
        if (!OPTION_MAP.containsKey(option)) {
            throw new IllegalArgumentException("유효하지 않는 옵셥입니다.");
        }
        return OPTION_MAP.get(option);
    }

}
