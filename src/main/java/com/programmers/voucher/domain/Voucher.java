package com.programmers.voucher.domain;

import java.util.Optional;
import java.util.UUID;

public interface Voucher {

    UUID getVoucherId();

    String getVoucherName();

    long getVoucherValue();

    VoucherType getVoucherType();

    Optional<UUID> getCustomerId();

    long discount(long beforeDiscount);
}
