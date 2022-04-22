package com.programmers.part1.domain.voucher;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId ();
    int getAmount();
    int discount(int beforeDiscount);
    String voucherTypeToString();
    String toString();
}
