package org.programmer.kdtspringboot.voucher;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();
    long getValue();
    long discount(long beforeDiscount);
}
