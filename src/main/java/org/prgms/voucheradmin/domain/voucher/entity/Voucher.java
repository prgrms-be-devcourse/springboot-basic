package org.prgms.voucheradmin.domain.voucher.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.prgms.voucheradmin.domain.voucher.entity.vo.VoucherType;

public interface Voucher {
    UUID getVoucherId();

    VoucherType getVoucherType();

    long getAmount();

    long discount(long beforeDiscount);

    LocalDateTime getCreatedAt();

    String toString();
}
