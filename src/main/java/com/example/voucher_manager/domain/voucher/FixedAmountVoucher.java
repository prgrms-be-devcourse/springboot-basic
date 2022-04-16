package com.example.voucher_manager.domain.voucher;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private final UUID voucherId;
    private final Long discountPrice;

    public FixedAmountVoucher(UUID voucherId, Long discountPrice) {
        this.voucherId = voucherId;
        this.discountPrice = discountPrice;
    }

    @Override
    public String toString() {
        return "[VoucherType : FixedAmountVoucher," + " VoucherId : " + voucherId + ", DiscountPrice : " + discountPrice + "$]";
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public Long discount(Long regularPrice) {
        return regularPrice - discountPrice;
    }
}
