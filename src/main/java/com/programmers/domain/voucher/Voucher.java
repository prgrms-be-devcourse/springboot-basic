package com.programmers.domain.voucher;

import java.util.UUID;

public interface Voucher {

    UUID getVoucherId();

    String getVoucherName();

    long getVoucherValue();

    VoucherType getVoucherType();

    long discount(long beforeDiscount);
}
