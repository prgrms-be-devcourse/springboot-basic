package org.prgrms.application.domain.voucher;

import java.util.Arrays;

public enum VoucherType {
    FIXED("fixed"),
    PERCENT("percent");

    private final String typeName;

    VoucherType(String typeName) {
        this.typeName = typeName;
    }

    public static VoucherType findBySelection(String selection) {
        return Arrays.stream(values())
                .filter(s -> s.name().equalsIgnoreCase(selection))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 바우처 형식 입력입니다."));
    }

}
