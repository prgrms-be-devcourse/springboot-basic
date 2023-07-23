package com.example.demo.domain.voucher;

import com.example.demo.enums.VoucherDiscountType;
import com.example.demo.view.validate.NumberValidator;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PercentDiscountVoucher implements Voucher {

    private final UUID id;
    private int discountAmount;

    @Builder
    public PercentDiscountVoucher(int discountAmount) {
        this.id = UUID.randomUUID();
        NumberValidator.validateAmount(VoucherDiscountType.PERCENT, discountAmount);
        this.discountAmount = discountAmount;
    }

    @Builder
    public PercentDiscountVoucher(UUID id, int discountAmount) {
        this.id = id;
        NumberValidator.validateAmount(VoucherDiscountType.PERCENT, discountAmount);
        this.discountAmount = discountAmount;
    }

    @Override
    public int discount(int beforeAmount) {
        return (int) (beforeAmount * ((100 - discountAmount) / 100.0));
    }

    @Override
    public void updateDiscountAmount(int discountAmount) {
        NumberValidator.validateAmount(VoucherDiscountType.PERCENT, discountAmount);
        this.discountAmount = discountAmount;
    }

    @Override
    public VoucherDiscountType getVoucherType() {
        return VoucherDiscountType.PERCENT;
    }
}
