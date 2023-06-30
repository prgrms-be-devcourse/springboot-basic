package com.prgms.voucher.voucherproject.domain;

import java.util.UUID;

public interface Voucher {
    UUID getId();
    long discount(long beforeDiscount);
}
