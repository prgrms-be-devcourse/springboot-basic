package org.programmers.kdt.weekly.voucher.model;


public interface Voucher {
    long discount(long beforeDiscount);
    VoucherType getVoucherType();
    int getValue();
}
