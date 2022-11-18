package org.programmers.springbootbasic.domain.voucher.model;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();

    long getAmount();

    long discount(long originalPrice);
}
