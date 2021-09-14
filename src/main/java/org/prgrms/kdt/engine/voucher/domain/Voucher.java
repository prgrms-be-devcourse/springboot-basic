package org.prgrms.kdt.engine.voucher.domain;

import org.prgrms.kdt.engine.voucher.VoucherType;

import java.time.LocalDateTime;
import java.util.UUID;

public interface Voucher {
    long discount(long beforeDiscount);
    UUID getVoucherId();
    long getVoucherRate();
    VoucherType getVoucherType();
    LocalDateTime getVoucherCreatedAt();
}
