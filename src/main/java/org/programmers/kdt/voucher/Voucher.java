package org.programmers.kdt.voucher;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();
    long getDiscount();
    VoucherType getVoucherType();

    long getDiscountedPrice(long beforeDiscount);

    VoucherStatus getStatus();
}
