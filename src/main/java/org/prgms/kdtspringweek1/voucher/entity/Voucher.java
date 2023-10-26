package org.prgms.kdtspringweek1.voucher.entity;

import java.util.UUID;

public interface Voucher {

    UUID getVoucherId();

    VoucherType getVoucherType();

    long getDiscountValue();
}
