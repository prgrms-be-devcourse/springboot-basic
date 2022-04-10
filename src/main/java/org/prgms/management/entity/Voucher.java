package org.prgms.management.entity;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();

    String getVoucherType();

    String getVoucherName();

    long getDiscountNum();
}
