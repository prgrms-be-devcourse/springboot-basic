package com.example.demo.domain.voucher;

import com.example.demo.enums.VoucherDiscountType;
import java.util.UUID;

public class VoucherFactory {

    public static Voucher createVoucher(UUID id, int discountAmount, VoucherDiscountType discountType) {

        return switch (discountType) {
            case FIX -> new FixedAmountVoucher(id, discountAmount);
            case PERCENT -> new PercentDiscountVoucher(id, discountAmount);
        };
    }
}
