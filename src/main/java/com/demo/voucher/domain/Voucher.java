package com.demo.voucher.domain;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();

    long discount(long beforeDiscount);

    long getAmount();

    String getVoucherTypeDescription();

    String getDiscountInfo();
}
