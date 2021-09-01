package org.prgrms.kdt.voucher;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();
    VoucherType getType();
    long getDiscount();
    long discount(long beforeDiscount);
    void changeDiscount(long discount);
    void validateDiscount(long discount);
}
