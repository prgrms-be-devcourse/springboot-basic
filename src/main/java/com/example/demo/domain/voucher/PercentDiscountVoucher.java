package com.example.demo.domain.voucher;

import com.example.demo.util.VoucherType;
import java.util.UUID;
import lombok.Getter;

@Getter
public class PercentDiscountVoucher implements Voucher {

    private final UUID id;
    private final double discountAmount;

    public PercentDiscountVoucher(double discountAmount) {
        this.id = UUID.randomUUID();
        this.discountAmount = discountAmount;
    }

    @Override
    public double discount(double beforeAmount) {
        return beforeAmount * ((100 - discountAmount) / 100.0);
    }

    @Override
    public VoucherType getVoucherType() {
        return VoucherType.PERCENT;
    }
}
