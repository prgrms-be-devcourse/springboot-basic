package org.prgms.voucher;

public enum AmountType {
    FIX("fix", 1),
    PERCENT("percent", 2);

    private final String name;
    private final int number;

    AmountType(String name, int number) {
        this.name = name;
        this.number = number;
    }
}
