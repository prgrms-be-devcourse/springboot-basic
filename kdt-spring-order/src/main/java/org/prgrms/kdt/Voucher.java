package org.prgrms.kdt;

import java.util.UUID;

public interface Voucher {
    UUID getVoucher();
    long discount(long beforeDiscount);
}
