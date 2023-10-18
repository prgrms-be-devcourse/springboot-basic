package org.programmers.springboot.basic.domain.voucher.entity;

import org.programmers.springboot.basic.domain.voucher.exception.VoucherNotFoundException;

import java.util.Arrays;

public enum VoucherType {

    FIXED(1),
    PERCENT(2);

    private final int type;

    VoucherType(int type) {
        this.type = type;
    }

    public int getType() {
        return this.type;
    }

    public static VoucherType valueOfVoucherByType(int type) {

        return Arrays.stream(VoucherType.values())
                .filter(voucher -> voucher.getType() == type)
                .findAny()
                .orElseThrow(VoucherNotFoundException::new);
    }
}
