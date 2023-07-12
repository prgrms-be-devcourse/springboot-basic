package com.example.demo.domain.voucher;

import com.example.demo.util.VoucherType;
import java.util.UUID;
import lombok.Getter;


@Getter
public class FixedAmountVoucher implements Voucher {

    private final UUID id;
    private final double discountAmount;

    public FixedAmountVoucher(double discountAmount) {
        this.id = UUID.randomUUID();
        this.discountAmount = discountAmount;
    }

    @Override
    public double discount(double beforeAmount) {
        double result = beforeAmount - discountAmount;
        return result < 0 ? 0 : result;
    }

    @Override
    public VoucherType getVoucherType() {
        return VoucherType.FIX;
    }
}
