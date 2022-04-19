package com.prgrms.management.voucher.domain;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();
    VoucherType getVoucherType();
    long getAmount();
    long discount(long beforeAmount);
}
