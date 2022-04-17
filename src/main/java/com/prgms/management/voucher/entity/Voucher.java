package com.prgms.management.voucher.entity;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();

    int getVoucherFigure();

    String getVoucherType();

    void resetVoucherId();

    void setName(String name);

    Long discount(Long beforeDiscount);

    String getStringForCSV();
}
