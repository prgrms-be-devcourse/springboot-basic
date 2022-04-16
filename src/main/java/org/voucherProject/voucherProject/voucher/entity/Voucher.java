package org.voucherProject.voucherProject.voucher.entity;

import java.time.LocalDateTime;
import java.util.UUID;

public interface Voucher {

    UUID getVoucherId();

    long discount(long beforeDiscount);

    VoucherType getVoucherType();

    VoucherStatus getVoucherStatus();

    long getHowMuch();

    LocalDateTime getCreatedAt();

    void useVoucher();

    void cancelVoucher();
}
