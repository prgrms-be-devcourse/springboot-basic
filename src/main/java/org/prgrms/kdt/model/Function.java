package org.prgrms.kdt.model;

import java.util.Arrays;

public enum Function {
    exit(" to exit the program"),
    create(" to create a new voucher"),
    list(" to list all vouchers");

    private String explain;

    Function(String explain) {
        this.explain = explain;
    }

    public String getExplain() {
        return explain;
    }

    public static boolean hasFunction(String funciton) {
        return Arrays.stream(Function.values())
                .anyMatch(f -> f.name().equals(funciton.trim()));
    }
}
