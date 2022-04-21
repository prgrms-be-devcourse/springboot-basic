package org.prgrms.kdt.shop.domain;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId( );

    long discount(long beforeDiscount);

    long getAmount( );
}
