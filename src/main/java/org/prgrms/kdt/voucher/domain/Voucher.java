package org.prgrms.kdt.voucher.domain;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();

    long discount(long beforeDiscount);

    long getAmount();

    int getPercent();

    UUID getCustomerId();

    void setCustomerId(UUID customerId);
}
