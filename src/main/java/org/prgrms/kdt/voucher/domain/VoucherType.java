package org.prgrms.kdt.voucher.domain;

import lombok.Getter;

@Getter
public enum VoucherType {

    FIXED(1, "FIXED"),
    PERCENT(2, "PERCENT"),
    FAIL(0, "FAIL");

    private String type;
    private int idx;

    VoucherType(int idx, String type) {
        this.type = type;
        this.idx = idx;
    }
}
