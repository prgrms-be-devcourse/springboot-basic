package org.prgrms.kdt.domain.voucher.model;

import org.prgrms.kdt.domain.voucher.types.VoucherType;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();

    VoucherType getVoucherType();

    long getDiscountValue();

    long discount(long beforeDiscount);
}
