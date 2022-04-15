package com.prgms.management.voucher.entity;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();

    int getVoucherFigure();

    String getVoucherType();

    void resetVoucherId();

    Long discount(Long beforeDiscount);

    String getStringForCSV();
}
