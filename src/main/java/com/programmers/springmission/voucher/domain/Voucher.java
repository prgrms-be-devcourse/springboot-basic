package com.programmers.springmission.voucher.domain;

import lombok.Getter;

import java.util.Objects;
import java.util.UUID;

@Getter
public class Voucher {

    private final UUID voucherId;
    private final VoucherPolicy voucherPolicy;
    private long voucherAmount;
    private UUID customerId;

    public Voucher(VoucherPolicy voucherPolicy, long voucherAmount) {
        voucherPolicy.validateAmount(voucherAmount);
        this.voucherId = UUID.randomUUID();
        this.voucherPolicy = voucherPolicy;
        this.voucherAmount = voucherAmount;
    }

    public Voucher(UUID voucherId, VoucherPolicy voucherPolicy, long voucherAmount) {
        voucherPolicy.validateAmount(voucherAmount);
        this.voucherId = voucherId;
        this.voucherPolicy = voucherPolicy;
        this.voucherAmount = voucherAmount;
    }

    public long discount(long beforeDiscount) {
        return voucherPolicy.discount(beforeDiscount, voucherAmount);
    }

    public void updateAmount(long voucherAmount) {
        voucherPolicy.validateAmount(voucherAmount);
        this.voucherAmount = voucherAmount;
    }

    public void updateCustomer(UUID customerId) {
        this.customerId = customerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Voucher voucher = (Voucher) o;
        return Objects.equals(voucherId, voucher.voucherId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(voucherId);
    }
}

