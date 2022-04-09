package org.programmers.kdtspring.entity.voucher;

import java.util.UUID;

public interface Voucher {

    UUID getVoucherId();

    long discount(long beforeDiscount);

    VoucherType getVoucherType();

}
