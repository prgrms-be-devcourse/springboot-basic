package org.prgms.kdt.application.voucher.domain;

import lombok.Getter;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();
    VoucherType getVoucherType();
    long discount(long beforeDiscount);
}
