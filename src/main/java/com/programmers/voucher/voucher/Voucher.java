package com.programmers.voucher.voucher;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();

    long discount(long beforeDiscount);

    String toString();

    long getValue();

    VoucherType getType();

    void changeAssigned(boolean isAssigned);

    boolean isAssigned();
}
