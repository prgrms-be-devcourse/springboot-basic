package org.programmers.voucher;

import java.util.UUID;

public interface Voucher {

    UUID getVoucherId();

    Long getDiscountValue();

    VoucherType getVoucherType();

    UUID getOwnerId();

    long discount(long beforeDiscount);

}
