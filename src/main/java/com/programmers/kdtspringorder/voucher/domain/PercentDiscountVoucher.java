package com.programmers.kdtspringorder.voucher.domain;

import com.programmers.kdtspringorder.voucher.VoucherType;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class PercentDiscountVoucher extends Voucher {

    public PercentDiscountVoucher(UUID voucherId, long percent) {
        this(voucherId, null, percent, VoucherType.PERCENT, false, LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS), null);
    }

    public PercentDiscountVoucher(UUID voucherId, UUID customerId, long percent, VoucherType type, boolean used, LocalDateTime createdAt, LocalDateTime expirationDate) {
        super(voucherId, customerId, percent, type, used, createdAt, expirationDate);
        if (percent <= 0 || percent > 50) {
            throw new IllegalArgumentException("percent should over than 0 And less or equal to 50");
        }
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - beforeDiscount * getDiscountValue() / 100;
    }
}
