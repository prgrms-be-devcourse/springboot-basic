package com.programmers.kdtspringorder.voucher.domain;

import java.util.UUID;

public interface Voucher {

    UUID getVoucherId();

    long discount(long beforeDiscount);

    long getValue();
}
