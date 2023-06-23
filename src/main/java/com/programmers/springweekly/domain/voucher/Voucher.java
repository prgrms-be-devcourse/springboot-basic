package com.programmers.springweekly.domain.voucher;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();

    long discount(long beforePrice);

    long getVoucherAmount();

    String getVoucherType();
}
