package org.prgrms.kdtspringdemo.voucher.model.entity;

import org.prgrms.kdtspringdemo.voucher.constant.VoucherType;

import java.util.UUID;

public interface Voucher {
    UUID getId();

    VoucherType getType();

    long getAmount();

    long executeDiscount(long originPrice);

    long validateAmount(long amount);
}
