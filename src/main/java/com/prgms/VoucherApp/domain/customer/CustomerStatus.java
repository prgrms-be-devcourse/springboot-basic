package com.prgms.VoucherApp.domain.customer;

public enum CustomerStatus {
    NORMAL, BLACKLIST;

    public boolean isBlackList() {
        return this == BLACKLIST;
    }
}
