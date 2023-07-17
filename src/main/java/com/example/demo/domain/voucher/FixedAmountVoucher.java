package com.example.demo.domain.voucher;

import com.example.demo.enums.VoucherDiscountType;
import com.example.demo.view.validate.NumberValidator;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;


@Getter
@ToString
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
        int result = beforeAmount - discountAmount;
        return result < 0 ? 0 : result;
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
