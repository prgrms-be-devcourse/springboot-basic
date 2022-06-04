package org.programs.kdt.Voucher.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public interface Voucher{

    long discount(long beforeDiscount);

    UUID getVoucherId();

    VoucherType getVoucherType();

    long getValue();

    void validation(long value);

    long getMaxValue();

    LocalDateTime getCreatedAt();

    void changeValue(Long value);

}
