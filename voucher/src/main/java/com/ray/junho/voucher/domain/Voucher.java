package com.ray.junho.voucher.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public abstract class Voucher {

    private final long id;
    private final VoucherPeriod voucherPeriod;
    private final VoucherType voucherType;

    protected Voucher(long id, LocalDateTime createdAt, LocalDateTime expireAt, VoucherType voucherType) {
        this.id = id;
        this.voucherType = voucherType;
        this.voucherPeriod = new VoucherPeriod(createdAt, expireAt);
    }

    public abstract Currency discount(Currency beforeDiscountPrice);

    public long getId() {
        return id;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Voucher voucher = (Voucher) o;
        return id == voucher.id && Objects.equals(voucherPeriod, voucher.voucherPeriod);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, voucherPeriod);
    }
}
