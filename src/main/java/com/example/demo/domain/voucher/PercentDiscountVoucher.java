package com.example.demo.domain.voucher;

import com.example.demo.util.VoucherType;
import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

    private final UUID id;
    private final double discountPercent;

    public PercentDiscountVoucher(double discountPercent) {
        this.id = UUID.randomUUID();
        this.discountPercent = discountPercent;
    }

    @Override
    public double discount(double beforeAmount) {
        return beforeAmount * ((100 - discountPercent) / 100.0);
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    @Override
    public double getDiscountAmount() {
        return discountPercent;
    }

    @Override
    public VoucherType getVoucherType() {
        return VoucherType.PERCENT;
    }
}
