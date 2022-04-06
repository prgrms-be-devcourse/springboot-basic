package org.prgms.voucheradmin.domain.voucher.entity;

import org.prgms.voucheradmin.domain.voucher.entity.vo.VoucherTypes;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();

    VoucherTypes getVoucherType();

    long discount(long beforeDiscount);


    String toString();
}
