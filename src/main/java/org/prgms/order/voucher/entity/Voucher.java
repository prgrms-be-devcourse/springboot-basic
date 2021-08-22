package org.prgms.order.voucher.entity;

import java.util.UUID;

public interface Voucher {

    UUID getVoucherId();

    long discount(long beforeDiscount);

    String getVoucherInfo();
}
