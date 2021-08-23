package org.prgrms.kdt.voucher.domain;

import lombok.Builder;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

    private final UUID voucherId;
    private final long discount;
    private final VoucherType voucherType = VoucherType.valueOf("FIXED");

    @Builder
    public FixedAmountVoucher(UUID voucherId, long discount) {
        this.voucherId = voucherId;
        this.discount = discount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public Long getDiscount() {
        return discount;
    }

    @Override
    public VoucherType getVoucherType() {
        return voucherType;
    }

    @Override
    public Long discount(long beforeDiscount) {
        return beforeDiscount - discount;
    }
}
