package com.prgms.voucher.voucherproject.domain.voucher;

import java.util.UUID;

public interface Voucher {
    UUID getId();
    long discount(long beforeDiscount);
    long getDiscount();
    VoucherType getVoucherType();
}
