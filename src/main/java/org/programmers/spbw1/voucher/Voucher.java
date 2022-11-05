package org.programmers.spbw1.voucher;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherID();
    long discount(long beforeDiscount);
}
