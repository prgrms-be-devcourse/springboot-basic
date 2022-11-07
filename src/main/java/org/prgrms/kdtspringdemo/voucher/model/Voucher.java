package org.prgrms.kdtspringdemo.voucher.model;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();
    long discount(long beforeDiscount);
    VoucherType getVoucherType();
    String discountValue();
}
