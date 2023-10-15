package com.zerozae.voucher.domain.voucher;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();
    long getDiscount();
    VoucherType getVoucherType();
    UseStatusType getUseStatusType();
}
