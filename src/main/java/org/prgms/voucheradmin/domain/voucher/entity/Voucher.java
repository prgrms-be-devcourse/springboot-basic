package org.prgms.voucheradmin.domain.voucher.entity;

import java.util.UUID;

import org.prgms.voucheradmin.domain.voucher.entity.vo.VoucherTypes;

public interface Voucher {
    UUID getVoucherId();

    VoucherTypes getVoucherType();

    long discount(long beforeDiscount);


    String toString();
}
