package com.waterfogsw.voucher.voucher.domain;

import java.util.UUID;

public class Voucher {
    UUID id;
    VoucherType type;
    Double value;

    public Voucher(UUID id, VoucherType type, Double value) {
        this.id = id;
        this.type = type;
        this.value = value;
    }

    public UUID getId() {
        return id;
    }

    public VoucherType getType() {
        return type;
    }

    public Double getValue() {
        return value;
    }
}
