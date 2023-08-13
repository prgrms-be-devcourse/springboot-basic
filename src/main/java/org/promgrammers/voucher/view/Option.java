package org.promgrammers.voucher.view;


import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public enum Option {

    EXIT("exit"),
    CREATE("create"),
    LIST("list");

    private final String option;


    public static Option fromOption(String option) {


        for (Option value : Option.values()) {
            if (value.option.equalsIgnoreCase(option)) {
                return value;
            }
        }
        throw new IllegalArgumentException("유효하지 않은 명령어 타입: " + option);
    }
}
