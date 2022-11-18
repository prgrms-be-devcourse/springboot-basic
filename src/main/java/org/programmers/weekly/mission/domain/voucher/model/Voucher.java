package org.programmers.weekly.mission.domain.voucher.model;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();
    String getType();
    long getDiscount();
    long discount(long beforeDiscount);
}
