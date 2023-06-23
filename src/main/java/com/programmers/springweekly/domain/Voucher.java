package com.programmers.springweekly.domain;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();

    long discount(long beforePrice);

    long getVoucherAmount();

    String getVoucherType();
}
