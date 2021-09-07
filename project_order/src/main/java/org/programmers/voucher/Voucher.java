package org.programmers.voucher;

import java.util.UUID;

public interface Voucher {

    UUID getVoucherId();

    Long getValue();

    VoucherType getVoucherType();

    long discount(long beforeDiscount);

}
