package com.programmers.kdtspringorder.voucher.domain;

import com.programmers.kdtspringorder.voucher.VoucherType;

import java.time.LocalDateTime;
import java.util.UUID;

public interface Voucher {


    UUID getVoucherId();

    long discount(long beforeDiscount);

    void allocateVoucherToCustomer(UUID customerId);

    void removeVoucherFromCustomer();

    void use();

    long getValue();

    VoucherType getType();

    boolean isUsed();

    LocalDateTime getCreatedAt();

    LocalDateTime getExpirationDate();

    UUID getCustomerId();
}
