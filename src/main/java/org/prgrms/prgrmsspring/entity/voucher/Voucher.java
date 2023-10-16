package org.prgrms.prgrmsspring.entity.voucher;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();

    long getAmount();

    long discount(long beforeDiscount);
}
