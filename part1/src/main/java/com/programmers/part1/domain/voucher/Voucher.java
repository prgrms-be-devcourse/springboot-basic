package com.programmers.part1.domain.voucher;

import java.time.LocalDateTime;
import java.util.UUID;

public interface Voucher {
    UUID getVoucherId ();
    int getAmount();
    VoucherType getVoucherType();
    int discount(int beforeDiscount);
    LocalDateTime getCreatedAt();
    String toString();
}
