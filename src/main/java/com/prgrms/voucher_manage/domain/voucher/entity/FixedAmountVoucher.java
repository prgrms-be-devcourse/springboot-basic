package com.prgrms.voucher_manage.domain.voucher.entity;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private final UUID voucherId;
    private final Long discountPrice;

    public FixedAmountVoucher(Long discountPrice){
        this.voucherId = UUID.randomUUID();
        this.discountPrice = discountPrice;
    }


    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public Long getDiscountAmount() {
        return discountPrice;
    }
}
