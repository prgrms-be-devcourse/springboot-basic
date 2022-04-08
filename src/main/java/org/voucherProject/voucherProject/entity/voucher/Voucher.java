package org.voucherProject.voucherProject.entity.voucher;

import java.util.UUID;

public interface Voucher {

    long discount(long beforeDiscount);

    UUID getVoucherId();

    VoucherType getVoucherType();

    long getHowMuch();

    VoucherStatus getVoucherStatus();

    void useVoucher();

    void cancelVoucher();

}
