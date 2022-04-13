package org.programmers.springbootbasic.voucher;


import java.util.UUID;

public interface Voucher {
    long discount(long beforeDiscount);

    UUID getId();

    int getAmount();
}
