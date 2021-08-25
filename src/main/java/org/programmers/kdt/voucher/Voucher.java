package org.programmers.kdt.voucher;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();
    long getDiscount();

    default long applyDiscount(long beforeDiscount) {
        return beforeDiscount - getDiscountAmount(beforeDiscount);
    }
    long getDiscountAmount(long beforeDiscount);
}
