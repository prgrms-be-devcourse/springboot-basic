package org.programmers.springbootbasic.voucher.model;

import java.time.LocalDateTime;
import java.util.UUID;

public interface Voucher {

    long discount(long beforeDiscount);

    UUID getVoucherId();

    VoucherType getVoucherType();

    long getValue();

    LocalDateTime getCreatedAt();

    Voucher changeValue(long value);
}
