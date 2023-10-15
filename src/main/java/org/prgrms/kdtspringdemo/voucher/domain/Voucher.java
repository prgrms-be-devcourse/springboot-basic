package org.prgrms.kdtspringdemo.voucher.domain;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();
    Long getAmount();
    String getVoucherType();
    long discount(long beforeDiscount);
}
