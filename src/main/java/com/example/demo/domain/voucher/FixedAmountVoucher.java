package com.example.demo.domain.voucher;

import com.example.demo.enums.VoucherDiscountType;
import com.example.demo.view.validate.NumberValidator;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;


@Getter
public class FixedAmountVoucher implements Voucher {

    private final UUID id;
    private int discountAmount;

    @Builder
    public FixedAmountVoucher(int discountAmount) {
        this.id = UUID.randomUUID();
        NumberValidator.validateAmount(VoucherDiscountType.FIX, discountAmount);
        this.discountAmount = discountAmount;
    }

    @Builder
    public FixedAmountVoucher(UUID id, int discountAmount) {
        this.id = id;
        NumberValidator.validateAmount(VoucherDiscountType.FIX, discountAmount);
        this.discountAmount = discountAmount;
    }

    @Override
    public int discount(int beforeAmount) {
        return Math.max(beforeAmount - discountAmount, 0);
    }

    @Override
    public VoucherDiscountType getVoucherType() {
        return VoucherDiscountType.FIX;
    }

    public void updateDiscountAmount(int discountAmount) {
        NumberValidator.validateAmount(VoucherDiscountType.FIX, discountAmount);
        this.discountAmount = discountAmount;
    }
}
