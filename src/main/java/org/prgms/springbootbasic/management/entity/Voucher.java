package org.prgms.springbootbasic.management.entity;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();

    String getVoucherName();

    long getDiscountNum();
}
