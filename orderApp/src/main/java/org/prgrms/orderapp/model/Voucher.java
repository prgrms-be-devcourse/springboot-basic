package org.prgrms.orderapp.model;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();
    long discount(long beforeDiscount);
    long getAmount();
}
