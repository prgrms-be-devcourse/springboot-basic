package com.prgms.management.voucher.entity;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();
    void resetVoucherId();
    Long discount(Long beforeDiscount);
}
