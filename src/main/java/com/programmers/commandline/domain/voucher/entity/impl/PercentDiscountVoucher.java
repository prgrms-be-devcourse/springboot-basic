package com.programmers.commandline.domain.voucher.entity.impl;

import com.programmers.commandline.domain.voucher.entity.Voucher;
import com.programmers.commandline.domain.voucher.entity.VoucherType;
import com.programmers.commandline.global.io.Message;

import java.time.LocalDateTime;
import java.util.UUID;

public class PercentDiscountVoucher extends Voucher {

    public PercentDiscountVoucher(UUID uuid, long discount, LocalDateTime createdAt) {
        super(uuid, VoucherType.PERCENT_DISCOUNT, discount, createdAt);
    }

    public static Voucher of(UUID uuid, long discount, LocalDateTime createdAt) {
        validation(discount);

        return new PercentDiscountVoucher(uuid, discount, createdAt);
    }

    private static void validation(long discount) {
        if (discount > 100 || discount < 0) {
            throw new IllegalArgumentException(Message.BAD_DISCOUNT.getMessage());
        }
    }
}
