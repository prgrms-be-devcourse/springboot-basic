package org.prgms.voucheradmin.domain.voucher.entity;

import org.prgms.voucheradmin.domain.voucher.entity.vo.VoucherType;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();

    VoucherType getVoucherType();

    long discount(long beforeDiscount);


    String toString();
}
