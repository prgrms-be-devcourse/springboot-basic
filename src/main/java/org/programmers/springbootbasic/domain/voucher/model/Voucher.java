package org.programmers.springbootbasic.domain.voucher.model;

import org.programmers.springbootbasic.data.VoucherType;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();

    long getAmount();

    VoucherType getVoucherType();

    long discount(long originalPrice);
}
