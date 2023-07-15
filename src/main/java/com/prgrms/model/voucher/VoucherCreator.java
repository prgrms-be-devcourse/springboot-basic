package com.prgrms.model.voucher;

import com.prgrms.model.voucher.discount.Discount;
import org.springframework.stereotype.Component;

@Component
public class VoucherCreator {
    private VoucherCreator() {
    }

    public Voucher createVoucher(int id, VoucherType voucherType, Discount discount) {

        return switch (voucherType) {
            case FIXED_AMOUNT_VOUCHER -> new FixedAmountVoucher(id, discount, voucherType);
            case PERCENT_DISCOUNT_VOUCHER -> new PercentDiscountVoucher(id, discount, voucherType);
        };
    }

}
