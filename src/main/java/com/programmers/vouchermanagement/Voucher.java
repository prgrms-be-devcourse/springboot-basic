package com.programmers.vouchermanagement;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();

    long discount(long priceBeforeDiscount);

}
