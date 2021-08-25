package org.prgrms.kdt.engine.voucher.domain;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();

    long discount(long beforeDiscount);
}
