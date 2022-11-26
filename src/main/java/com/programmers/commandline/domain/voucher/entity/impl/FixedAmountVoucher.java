package com.programmers.commandline.domain.voucher.entity.impl;

import com.programmers.commandline.domain.voucher.entity.Voucher;
import com.programmers.commandline.domain.voucher.entity.VoucherType;
import com.programmers.commandline.global.io.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.UUID;

public class FixedAmountVoucher extends Voucher {
    private static final Logger logger = LoggerFactory.getLogger(FixedAmountVoucher.class);

    private FixedAmountVoucher(UUID uuid, long discount, LocalDateTime createdAt) {
        super(uuid, VoucherType.FIXED_AMOUNT, discount, createdAt);
    }

    public static Voucher of(UUID uuid, long discount, LocalDateTime createdAt) {
        validation(discount);
        return new FixedAmountVoucher(uuid, discount, createdAt);
    }

    private static void validation(long discount) {
        if (discount < 0) {
            logger.info(Message.BAD_DISCOUNT.getMessage());
            throw new IllegalArgumentException(Message.BAD_DISCOUNT.getMessage());
        }
    }
}
