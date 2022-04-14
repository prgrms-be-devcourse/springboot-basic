package org.prgrms.kdt.voucher.model;

import java.util.Arrays;

public enum VoucherType {
    FIXED("fixed"),
    PERCENT("percent"),
    NONE("none");

    private final String keyword;

    VoucherType(String keyword) {
        this.keyword = keyword;
    }

    public static VoucherType findVoucherMenu(String keyword) {
        return Arrays.stream(VoucherType.values())
            .filter(v -> v.getKeyword().equals(keyword))
            .findFirst()
            .orElse(VoucherType.NONE);
    }

    public String getKeyword() {
        return keyword;
    }

}
