package com.programmers.part1.domain;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId ();
    long discount(long beforeDiscount);
}
