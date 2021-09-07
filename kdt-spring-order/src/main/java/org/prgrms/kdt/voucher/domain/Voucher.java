package org.prgrms.kdt.voucher.domain;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();
    long getValue();
    Enum<VoucherType> getVoucherType();
    public long discount(long beforeDiscount);
}
