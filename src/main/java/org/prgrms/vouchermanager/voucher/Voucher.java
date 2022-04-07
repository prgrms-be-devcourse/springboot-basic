package org.prgrms.vouchermanager.voucher;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();
    VoucherType getVoucherType();
    long discount(long beforeDiscount);
}
