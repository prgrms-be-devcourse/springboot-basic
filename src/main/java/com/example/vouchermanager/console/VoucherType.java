package com.example.vouchermanager.console;

import lombok.Getter;

@Getter
public enum VoucherType {
    FIXED("fixed"),
    PERCENT("precent");

    private String type;

    VoucherType(String type) {
    }
}
