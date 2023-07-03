package com.wonu606.vouchermanager.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public enum VoucherMenu {

    START,
    EXIT,
    CREATE,
    LIST;

    public static VoucherMenu getVoucherTypeByName(String name) {
        return Arrays.stream(values())
                .filter(v -> Objects.equals(v.name(), name.toUpperCase()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 메뉴입니다: " + name));
    }

    public static List<String> getAllNames() {
        return Arrays.stream(VoucherMenu.values())
                .map(v -> v.name().toLowerCase())
                .collect(Collectors.toList());
    }
}
