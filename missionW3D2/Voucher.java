package com.prgms.missionW3D2;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();
    long discount(long beforeDiscount);
    String getVoucherInfo();
}
