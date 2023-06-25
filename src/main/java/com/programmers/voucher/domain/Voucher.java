package com.programmers.voucher.domain;

import java.util.UUID;

public interface Voucher {

    UUID getVoucherId();

    long getDiscountAmount();

    long discount(long beforeAmount);

}
