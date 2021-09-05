package org.prgrms.kdt.voucher.model;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();
    long getVoucherValue();
    Enum<VoucherType> getVoucherType();
    public long discount(long beforeDiscount);
}
