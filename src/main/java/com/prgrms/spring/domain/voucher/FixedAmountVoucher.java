package com.prgrms.spring.domain.voucher;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FixedAmountVoucher implements Voucher{

    private final UUID voucherId;
    private final long discount;

    public static FixedAmountVoucher newInstance(UUID voucherId, long discount) {
        return new FixedAmountVoucher(voucherId, discount);
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
