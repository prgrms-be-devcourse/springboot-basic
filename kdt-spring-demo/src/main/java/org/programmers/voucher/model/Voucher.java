package org.programmers.voucher.model;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();

    long discount(long beforeDiscount);

    long getVoucherValue();

    VoucherType getVoucherType();
}
