package org.prgrms.kdt.model.voucher;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();

    long getDiscountAmount();
}
