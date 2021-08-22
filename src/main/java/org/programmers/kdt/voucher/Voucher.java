package org.programmers.kdt.voucher;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();
    long getDiscount();
    default long discount(long beforeDiscount) {
        return beforeDiscount - fetchDiscountAmount(beforeDiscount);
    }
    long fetchDiscountAmount(long beforeDiscount);
}
