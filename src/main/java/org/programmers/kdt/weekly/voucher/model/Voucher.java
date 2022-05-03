package org.programmers.kdt.weekly.voucher.model;


import java.time.LocalDateTime;
import java.util.UUID;

public interface Voucher {

    long discount(long beforeDiscount);

    long getValue();

    void changeValue(long value);

    LocalDateTime getCreatedAt();

    UUID getVoucherId();

    String getVoucherType();

    String serializeVoucher();
}
