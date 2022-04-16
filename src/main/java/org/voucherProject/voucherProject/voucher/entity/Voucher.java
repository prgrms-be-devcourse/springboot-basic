package org.voucherProject.voucherProject.voucher.entity;

import java.time.LocalDateTime;
import java.util.UUID;

public interface Voucher {

    long discount(long beforeDiscount);

    UUID getVoucherId();

    VoucherType getVoucherType();

    long getHowMuch();

    VoucherStatus getVoucherStatus();

    LocalDateTime getCreatedAt();

    void useVoucher();

    void cancelVoucher();

}
