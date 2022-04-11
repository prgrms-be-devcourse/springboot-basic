package org.prgrms.kdtspringdemo.domain.voucher;

public class PercentDiscountVoucher extends Voucher {
    @Override
    public long discount(long beforeDiscount) {
        return 0;
    }
}
