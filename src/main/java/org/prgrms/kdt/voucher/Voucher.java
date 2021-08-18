package org.prgrms.kdt.voucher;

import java.util.UUID;

public interface Voucher {
    void validate(UUID voucherId, long amount);

    UUID getVoucherId();

    long discount(long beforeDiscount);

    String toString();
}
