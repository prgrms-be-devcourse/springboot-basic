package com.prgrms.kdt.springbootbasic.entity.voucher;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class DefaultNotUsedVoucher extends Voucher{
    public DefaultNotUsedVoucher() {
        super(null, LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
    }

    @Override
    public UUID getVoucherId() {
        return null;
    }

    @Override
    public long getDiscountedMoney(long beforeDiscount) {
        return 0;
    }

    @Override
    public long getDiscountAmount() {
        return 0;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return LocalDateTime.now();
    }

    @Override
    public String getVoucherType() {
        return null;
    }

    @Override
    public long setAmount(long amount) {
        return 0;
    }
}
