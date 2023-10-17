package com.weeklyMission.voucher.domain;

import java.util.UUID;

public interface Voucher {

    UUID getVoucherId();

    long getAmount();

    long discount(long beforeDiscount);
}
