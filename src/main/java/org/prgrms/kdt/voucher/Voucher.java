package org.prgrms.kdt.voucher;

import java.util.UUID;

public interface Voucher {
    UUID voucherId();

    long discount(long beforeDiscount);

    String toString();
}
