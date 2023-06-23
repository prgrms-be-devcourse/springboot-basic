package com.prgrms.spring.domain.voucher;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PercentDiscountVoucher implements Voucher{

    private final UUID voucherId;
    private final long discount;

    public static PercentDiscountVoucher newInstance(UUID voucherId, long discount) {
        return new PercentDiscountVoucher(voucherId, discount);
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long getDiscount() {
        return discount;
    }
}
