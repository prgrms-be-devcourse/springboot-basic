package com.dojinyou.devcourse.voucherapplication.voucher.domain;


public abstract class Voucher {
    private final Long id;
    private final VoucherType type;
    private final VoucherAmount amount;

    public Voucher(VoucherType type, VoucherAmount amount) {
        this(null, type, amount);
    }

    public Voucher(Long id, VoucherType type, VoucherAmount amount) {
        this.id = id;
        this.type = type;
        this.amount = amount;
    }


    public Long getVoucherId() {
        return this.id;
    }

    public VoucherType getVoucherType() {
        return this.type;
    }

    public VoucherAmount getVoucherAmount() {
        return this.amount;
    }

    public abstract double discount(double originAmount);
}
