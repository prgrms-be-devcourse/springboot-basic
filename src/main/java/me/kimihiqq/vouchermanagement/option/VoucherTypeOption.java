package me.kimihiqq.vouchermanagement.option;

import java.util.Arrays;

public enum VoucherTypeOption implements ConsoleOption {
    FIXED(1, "Fixed amount voucher"),
    PERCENT(2, "Percent discount voucher");

    private final int key;
    private final String description;

    VoucherTypeOption(int key, String description) {
        this.key = key;
        this.description = description;
    }

    @Override
    public int getKey() {
        return key;
    }

    @Override
    public String getDescription() {
        return description;
    }
}