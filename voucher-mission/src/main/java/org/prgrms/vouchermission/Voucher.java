package org.prgrms.vouchermission;

public interface Voucher {

    long getVoucherId();
    long discountAmount(long beforeDiscount);
    Voucher injectVoucherId(long voucherId);
}
