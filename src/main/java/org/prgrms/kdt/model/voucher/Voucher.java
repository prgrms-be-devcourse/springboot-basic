package org.prgrms.kdt.model.voucher;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();
    long getVoucherValue();
    long discount(long beforeDiscount);
}
