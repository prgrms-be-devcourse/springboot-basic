package com.programmers.domain.voucher;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();

    long discount(long beforeDiscount);

    boolean available();

    Integer parsedDate();

    Integer expirationDate();
}
