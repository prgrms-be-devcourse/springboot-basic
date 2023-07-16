package com.example.demo.domain.voucher;

import com.example.demo.util.VoucherDiscountType;
import com.example.demo.view.validate.NumberValidator;
import java.util.UUID;
import lombok.Getter;


@Getter
public class FixedAmountVoucher implements Voucher {

    private final UUID id;
    private double discountAmount;

    public FixedAmountVoucher(double discountAmount) {
        this.id = UUID.randomUUID();
        NumberValidator.validateAmount(VoucherDiscountType.FIX, discountAmount);
        this.discountAmount = discountAmount;
    }

    @Override
    public double discount(double beforeAmount) {
        double result = beforeAmount - discountAmount;
        return result < 0 ? 0 : result;
    }

    @Override
    public VoucherDiscountType getVoucherType() {
        return VoucherDiscountType.FIX;
    }

    public void updateDiscountAmount(double discountAmount) {
        NumberValidator.validateAmount(VoucherDiscountType.FIX, discountAmount);
        this.discountAmount = discountAmount;
    }
}
