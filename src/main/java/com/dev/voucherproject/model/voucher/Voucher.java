package com.dev.voucherproject.model.voucher;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();

    long discount(long beforeDiscount);

    long getDiscountNumber();
}
