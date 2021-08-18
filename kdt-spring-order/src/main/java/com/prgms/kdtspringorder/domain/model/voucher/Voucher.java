package com.prgms.kdtspringorder.domain.model.voucher;

import java.util.UUID;

public interface Voucher {
    long discount(long beforeDiscountAmount);

    void useVoucher();

    UUID getVoucherId();

    long getDiscount();
}
