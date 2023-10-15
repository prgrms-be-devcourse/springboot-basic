package com.prgrms.voucher_manage.domain.voucher.entity;

import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class FixedAmountVoucher implements Voucher {
    private final UUID voucherId;
    private final Long discountPrice;


    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public Long getDiscountAmount() {
        return discountPrice;
    }
}
