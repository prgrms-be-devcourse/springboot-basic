package org.prgms.voucher.entity;

import java.util.UUID;

public interface Voucher {
    long discount(long beforeDiscount);

    UUID getVoucherId();
}
