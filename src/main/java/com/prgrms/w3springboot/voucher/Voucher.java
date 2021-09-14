package com.prgrms.w3springboot.voucher;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();

    long getAmount();

    VoucherType getVoucherType();

    long discount(long beforeDiscount);

    void validate(long value);
}
