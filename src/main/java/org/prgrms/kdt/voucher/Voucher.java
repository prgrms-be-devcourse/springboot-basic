package org.prgrms.kdt.voucher;

import java.util.UUID;

public interface Voucher {
    long discount(Long beforeDiscount);
    UUID getId();
    String toString();

    VoucherType getType();
    long getDiscount();
}
