package org.prgms;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherID();
    long discount(long beforeDiscount);
}
