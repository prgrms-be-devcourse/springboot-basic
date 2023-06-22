package com.prgms.VoucherApp.domain;

import java.util.UUID;

public interface Voucher {
    long discount(long beforeAmount);

    UUID getUUID();
}
