package org.prgrms.kdtspringdemo.voucher.model.entity;

import org.prgrms.kdtspringdemo.voucher.constant.VoucherType;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();

    VoucherType getVoucherType();

    long getDiscount();

    long executeDiscount(long originPrice);

    long validateDiscount(long discount);
}
