package org.programmers.springboot.basic.domain.voucher.entity;

import java.util.UUID;

public record PercentDiscountVoucher(UUID voucherId, Long discount, VoucherType voucherType) implements Voucher {

    @Override
    public UUID getVoucherId() {
        return this.voucherId;
    }

    @Override
    public VoucherType getVoucherType() {
        return this.voucherType;
    }

    @Override
    public Long getDiscount() {
        return this.discount;
    }
}
