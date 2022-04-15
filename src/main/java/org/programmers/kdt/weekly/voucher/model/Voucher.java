package org.programmers.kdt.weekly.voucher.model;


import java.util.UUID;

public interface Voucher {

    long discount(long beforeDiscount);

    VoucherType getVoucherType();

    int getValue();

    UUID getVoucherId();
}
