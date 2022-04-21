package org.prgrms.voucherapp.engine.voucher.entity;

import java.util.UUID;

public interface Voucher {

    long getAmount();

    UUID getVoucherId();

    String getTypeName();

    long discount(long beforeDiscount);
}
