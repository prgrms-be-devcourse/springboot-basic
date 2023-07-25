package com.wonu606.vouchermanager.menu;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public enum ConsoleMenu {
    START,
    CUSTOMER,
    VOUCHER,
    EXIT;

    public static ConsoleMenu getTypeByName(String name) {
        return Arrays.stream(values())
                .filter(c -> Objects.equals(c.name(), name.toUpperCase()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 메뉴입니다: " + name));
    }

    public static List<String> getAllNames() {
        return Arrays.stream(values())
                .map(c -> c.name().toLowerCase())
                .collect(Collectors.toList());
    }

    public boolean isNotExit() {
        return this != EXIT;
    }

}
