package com.programmers.vouchermanagement.voucher;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();

    long discount(long priceBeforeDiscount);

    boolean validatePositiveDiscount();

}
