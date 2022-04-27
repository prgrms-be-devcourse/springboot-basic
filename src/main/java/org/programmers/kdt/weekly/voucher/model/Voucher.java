package org.programmers.kdt.weekly.voucher.model;


public interface Voucher {

    long discount(long beforeDiscount);

    long getValue();

    void changeValue(int value);

    String serializeVoucher();
}
