package com.prgms.voucher.voucherproject.domain.voucher;

import builder.builderEntity.Entity;

import java.util.UUID;

public interface Voucher extends Entity {
    UUID getId();
    long getDiscountedAmount(long beforeDiscount);
    long getDiscount();
    VoucherType getVoucherType();
}
