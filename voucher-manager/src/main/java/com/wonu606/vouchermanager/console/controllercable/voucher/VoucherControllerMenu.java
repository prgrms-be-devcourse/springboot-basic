package com.wonu606.vouchermanager.console.controllercable.voucher;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public enum VoucherControllerMenu {

    START,
    EXIT,
    CREATE,
    LIST,
    ASSIGN,
    CUSTOMER_LIST;

    public static VoucherControllerMenu getTypeByName(String name) {
        return Arrays.stream(values())
                .filter(v -> Objects.equals(v.name(), name.toUpperCase()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 메뉴입니다: " + name));
    }

    public static List<String> getAllNames() {
        return Arrays.stream(values())
                .map(v -> v.name().toLowerCase())
                .collect(Collectors.toList());
    }

    public boolean isNotExit() {
        return this != EXIT;
    }
}
