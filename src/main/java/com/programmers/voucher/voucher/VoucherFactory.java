package com.programmers.voucher.voucher;

import java.util.UUID;

import static com.programmers.message.ErrorMessage.VOUCHER_INPUT_ERROR_MESSAGE;

public class VoucherFactory {
    public static Voucher createVoucher(UUID id, VoucherType type, long value) {
        return VoucherFactory.createVoucher(id, type, value, false);
    }

    public static Voucher createVoucher(VoucherType type, long value) {
        return VoucherFactory.createVoucher(UUID.randomUUID(), type, value);
    }


    public static Voucher createVoucher(UUID id, VoucherType type, long value, boolean isAssigned) {
        if (type == null) {
            throw new RuntimeException(VOUCHER_INPUT_ERROR_MESSAGE.getMessage());
        }

        if (type == VoucherType.FixedAmount) {
            return new FixedAmountVoucher(value, id, isAssigned);
        }

        return new PercentDiscountVoucher(id, value, isAssigned);
    }
}