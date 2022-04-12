package com.waterfogsw.voucher.voucher.domain;

import java.util.UUID;

public class Voucher {
    private UUID id;
    private VoucherType type;
    private Double value;

    public Voucher(UUID id, VoucherType type, Double value) {
        this.id = id;
        this.type = type;
        this.value = value;
    }

    public UUID getId() {
        return id;
    }

    public Double getValue() {
        return value;
    }

    public VoucherType getType() {
        return type;
    }
}
