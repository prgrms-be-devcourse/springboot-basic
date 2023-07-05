package org.prgms.vouchermanagement.voucher.domain.entity;

import org.prgms.vouchermanagement.voucher.VoucherType;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();
    long returnDiscount ();
    VoucherType getVoucherType();
}
