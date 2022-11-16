package org.programmers.weekly.mission.domain.voucher.model;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();
    long getVoucherInfo();
    long discount(long beforeDiscount);
}
