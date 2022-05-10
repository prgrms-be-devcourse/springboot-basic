package org.voucherProject.voucherProject.voucher.entity;

import java.time.LocalDateTime;
import java.util.UUID;

public interface Discountable {

    UUID getVoucherId();

    long discount(long beforeDiscount);

    VoucherType getVoucherType();

    VoucherStatus getVoucherStatus();

    long getHowMuch();

    LocalDateTime getCreatedAt();

    UUID getCustomerId();

    void useVoucher();

    void cancelVoucher();
}
