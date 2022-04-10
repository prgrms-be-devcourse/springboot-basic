package com.example.voucher_manager.domain.voucher;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher{
    private final UUID voucherId;
    private final Long discountPercent;

    public PercentDiscountVoucher(UUID voucherId, Long discountPercent) {
        this.voucherId = voucherId;
        this.discountPercent = discountPercent;
    }

    @Override
    public String toString() {
        return "[VoucherType : PercentDiscountVoucher," + " VoucherId : " + voucherId + ", DiscountPercent : " + discountPercent + "%]";
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public Long discount(Long regularPrice) {
        return regularPrice * (1 - (discountPercent / 100L));
    }
}
