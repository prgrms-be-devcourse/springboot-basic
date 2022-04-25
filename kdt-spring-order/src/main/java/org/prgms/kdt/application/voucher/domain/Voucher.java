package org.prgms.kdt.application.voucher.domain;

import java.time.LocalDateTime;
import lombok.Getter;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();

    VoucherType getVoucherType();

    UUID getCustomerId();

    long getDiscountValue();

    LocalDateTime getCreatedAt();

    long discount(long beforeDiscount);

    void changeDiscountValue(long discountValue);

    void validateDiscountValue(long discountValue);
}
