package org.prgrms.kdt.domain.voucher.model;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();

    VoucherType getVoucherType();

    long getDiscountValue();

    long discount(long beforeDiscount);

}
