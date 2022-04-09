package org.programmers.kdtspringvoucherweek1.voucher;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();

    long discount(long beforeDiscount);

    long getDiscount();
}
