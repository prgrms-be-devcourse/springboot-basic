package com.prgrms.spring.domain.menu;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public enum MenuType {
    EXIT("exit", "exit the program"),
    CREATE_VOUCHER("create", "create a new voucher"),
    LIST_VOUCHER("list", "list all vouchers");

    private final String name;
    private final String explain;

    public static MenuType matchType(String type) {
        return Arrays.stream(values())
                .filter(v -> type.equals(v.name))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }
}
