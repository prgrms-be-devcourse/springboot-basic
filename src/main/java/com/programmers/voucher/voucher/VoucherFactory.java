package com.programmers.voucher.voucher;

import java.util.UUID;

import static com.programmers.voucher.menu.Message.VOUCHER_INPUT_ERROR_MESSAGE;

public class VoucherFactory {
    public static Voucher createVoucher(UUID id, VoucherType type, long value) {
        if (type == null) {
            throw new RuntimeException(VOUCHER_INPUT_ERROR_MESSAGE.getMessage());
        }
        if (type == VoucherType.FixedAmount) {
            return new FixedAmountVoucher(id, value);
        }

        return new PercentDiscountVoucher(id, value);
    }

    public static Voucher createVoucher(VoucherType type, long value) {
        return VoucherFactory.createVoucher(UUID.randomUUID(), type, value);
    }
}