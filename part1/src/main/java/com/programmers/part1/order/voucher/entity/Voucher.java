package com.programmers.part1.order.voucher.entity;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId ();
    long discount(long beforeDiscount);
}
