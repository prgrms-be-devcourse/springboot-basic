package com.example.demo.domain.voucher;

import com.example.demo.util.VoucherDiscountType;
import com.example.demo.view.validate.NumberValidator;
import java.util.UUID;
import lombok.Getter;

@Getter
public class PercentDiscountVoucher implements Voucher {

    private final UUID id;
    private double discountAmount;

    public PercentDiscountVoucher(double discountAmount) {
        this.id = UUID.randomUUID();
        NumberValidator.validateAmount(VoucherDiscountType.PERCENT, discountAmount);
        this.discountAmount = discountAmount;
    }

    @Override
    public double discount(double beforeAmount) {
        return beforeAmount * ((100 - discountAmount) / 100.0);
    }

    public void updateDiscountAmount(double discountAmount) {
        NumberValidator.validateAmount(VoucherDiscountType.PERCENT, discountAmount);
        this.discountAmount = discountAmount;
    }

    @Override
    public VoucherDiscountType getVoucherType() {
        return VoucherDiscountType.PERCENT;
    }
}
