package org.programmers.springbootbasic.domain;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();

    long getAmount();

    long discount(long originalPrice);
}
