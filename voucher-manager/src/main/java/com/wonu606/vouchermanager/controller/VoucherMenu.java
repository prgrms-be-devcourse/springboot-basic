package com.wonu606.vouchermanager.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public enum VoucherMenu {

    EXIT,
    CREATE,
    LIST;

    public static String menuDescription;

    public static Optional<VoucherMenu> getVoucherTypeByName(String name) {
        return Arrays.stream(VoucherMenu.values())
                .filter(v -> v.name().equals(name.toUpperCase()))
                .findFirst();
    }

    public static List<String> getAllNames() {
        return Arrays.stream(VoucherMenu.values())
                .map(v -> v.name().toLowerCase())
                .collect(Collectors.toList());
    }
}
