package org.voucherProject.voucherProject.entity.voucher;

import java.util.UUID;

public interface Voucher {

    long discount(long beforeDiscount);

    UUID getVoucherId();

    VoucherType getVoucherType();

    void useVoucher();

    void cancelVoucher();

}
