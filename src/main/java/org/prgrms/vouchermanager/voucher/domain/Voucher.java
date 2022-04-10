package org.prgrms.vouchermanager.voucher.domain;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();
    VoucherType getVoucherType();
    long discount(long beforeDiscount);
}
