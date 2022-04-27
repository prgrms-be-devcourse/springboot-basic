package com.programmers.part1.domain.voucher;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId ();
    int getAmount();
    VoucherType getVoucherType();
    int discount(int beforeDiscount);
    String toString();
}
