package org.prgms.voucheradmin.domain.voucher.entity;

import java.util.UUID;

import org.prgms.voucheradmin.domain.voucher.entity.vo.VoucherType;

public interface Voucher {
    UUID getVoucherId();

    VoucherType getVoucherType();

    long discount(long beforeDiscount);

    String toString();
}
