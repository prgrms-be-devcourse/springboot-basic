package org.prgrms.kdt.domain.command.types;

import java.util.Arrays;

public enum CommandType {
    CREATE_CUSTOMER("1"),
    LIST_CUSTOMER("2"),
    LIST_CUSTOMER_HAS_VOUCHER("3"),
    BLACKLIST_CUSTOMER("4"),
    CREATE_VOUCHER("5"),
    LIST_VOUCHER("6"),
    LIST_VOUCHER_HAS_CUSTOMER("7"),
    REMOVE_VOUCHER("8"),
    ASSIGN_VOUCHER("9"),
    EXIT("10");

    private final String num;

    CommandType(String num) {
        this.num = num;
    }

    public static CommandType findCommand(String num) {
        return Arrays.stream(values())
                .filter(value -> value.num.equals(num))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("지원하지 않는 명령어입니다."));
    }
}
