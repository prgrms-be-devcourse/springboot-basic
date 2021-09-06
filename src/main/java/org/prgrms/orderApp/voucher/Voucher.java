package org.prgrms.orderApp.voucher;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();
    long discount(long beforeDiscount);
    long getVoucherAmount();
}
