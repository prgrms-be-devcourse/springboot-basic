package org.programmers.voucher.model;

import java.util.UUID;

public interface VoucherInterface {
    UUID getVoucherId();

    long discount(long beforeDiscount);

    long getVoucherValue();

    VoucherType getVoucherType();
}
