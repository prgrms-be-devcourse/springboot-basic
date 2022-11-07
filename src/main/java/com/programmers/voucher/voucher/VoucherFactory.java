package com.programmers.voucher.voucher;

import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.programmers.voucher.voucher.VoucherList.FixedAmount;

@Component
public class VoucherFactory {
    public static Voucher createVoucher(UUID id, VoucherList type, long value) {
        if (type == FixedAmount) {
            return new FixedAmountVoucher(value, id);
        }

        return new PercentDiscountVoucher(id, value);
    }

    public static Voucher createVoucher(VoucherList type, long value) {
        return VoucherFactory.createVoucher(UUID.randomUUID(), type, value);
    }
}
