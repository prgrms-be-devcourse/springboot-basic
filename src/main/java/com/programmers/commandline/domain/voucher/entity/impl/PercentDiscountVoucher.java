package com.programmers.commandline.domain.voucher.entity.impl;

import com.programmers.commandline.domain.voucher.entity.Voucher;
import com.programmers.commandline.domain.voucher.entity.VoucherType;
import com.programmers.commandline.global.io.Message;

import java.time.LocalDateTime;
import java.util.UUID;

public class PercentDiscountVoucher extends Voucher {

    private static final int MAX_PERCENT = 100;
    private static final int MIN_PERCENT = 0;


    public PercentDiscountVoucher(UUID uuid, long discount, LocalDateTime createdAt) {
        super(uuid, VoucherType.PERCENT_DISCOUNT, discount, createdAt);
    }

    public static Voucher of(UUID uuid, long discount, LocalDateTime createdAt) {
        validation(discount);

        return new PercentDiscountVoucher(uuid, discount, createdAt);
    }

    private static void validation(long discount) {
        if (discount > MAX_PERCENT || discount <= MIN_PERCENT) {
            throw new IllegalArgumentException(Message.BAD_DISCOUNT.getMessage());
        }
    }
}
