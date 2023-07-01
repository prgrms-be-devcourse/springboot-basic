package org.prgrms.assignment.voucher.model;

import java.time.LocalDateTime;
import java.util.UUID;

public interface Voucher {

    UUID getVoucherId();

    long discount(long beforeDiscount);

    long getBenefit();

    VoucherType getVoucherType();

    String getVoucherName();

    LocalDateTime getCreatedAt();
}
