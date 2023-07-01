package com.programmers.vouchermanagement.voucher.application;

import com.programmers.vouchermanagement.voucher.domain.DiscountType;
import com.programmers.vouchermanagement.voucher.domain.FixedAmountVoucher;
import com.programmers.vouchermanagement.voucher.domain.PercentDiscountVoucher;
import com.programmers.vouchermanagement.voucher.domain.Voucher;

public class VoucherFactory {

    public static Voucher createVoucher(DiscountType discountType, int discountAmount) {
        if (discountType == DiscountType.FIX) {
            return new FixedAmountVoucher(discountAmount);
        }
        return new PercentDiscountVoucher(discountAmount);
    }
}
