package com.prgrms.voucher_manage.domain.voucher.entity;

import java.util.UUID;

public class PercentAmountVoucher implements Voucher {
    private final UUID voucherId;
    private final Long discountPercent;

    public PercentAmountVoucher(Long discountPercent){
        this.voucherId = UUID.randomUUID();
        this.discountPercent = discountPercent;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public Long getDiscountAmount() {
        return discountPercent;
    }

}
