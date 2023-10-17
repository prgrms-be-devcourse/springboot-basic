package com.prgrms.vouchermanager.console;

import lombok.Getter;

@Getter
public enum VoucherType {
    FIXED("fixed"),
    PERCENT("precent");

    private final String type = null;

    VoucherType(String type) {
    }
}
