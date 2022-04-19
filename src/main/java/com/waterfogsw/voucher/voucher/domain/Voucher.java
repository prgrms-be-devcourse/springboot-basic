package com.waterfogsw.voucher.voucher.domain;

public class Voucher {
    Long id;
    VoucherType type;
    int value;

    public Voucher(Long id, VoucherType type, int value) {
        this.id = id;
        this.type = type;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public VoucherType getType() {
        return type;
    }

    public int getValue() {
        return value;
    }
}
