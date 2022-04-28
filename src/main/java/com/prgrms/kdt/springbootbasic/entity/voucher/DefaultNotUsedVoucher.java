package com.prgrms.kdt.springbootbasic.entity.voucher;

import java.time.LocalDateTime;
import java.util.UUID;

public class DefaultNotUsedVoucher extends Voucher{
    public DefaultNotUsedVoucher() {
        this.createdAt = LocalDateTime.now();
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
}
