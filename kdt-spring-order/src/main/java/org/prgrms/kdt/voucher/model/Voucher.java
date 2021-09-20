package org.prgrms.kdt.voucher.model;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();
    long getValue();
    Enum<VoucherType> getVoucherType();
    public long discount(long beforeDiscount);
}
