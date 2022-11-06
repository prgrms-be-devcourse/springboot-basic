package com.programmers.voucher.domain.voucher;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();

    long discount(long beforeDiscount);

    String toString();

    long getValue();
}
