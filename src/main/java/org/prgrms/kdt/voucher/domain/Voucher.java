package org.prgrms.kdt.voucher.domain;

import java.util.UUID;

public interface Voucher {
    void validate(UUID voucherId, long value);

    UUID voucherId();

    long discount(long beforeDiscount);

    String toString();
}
