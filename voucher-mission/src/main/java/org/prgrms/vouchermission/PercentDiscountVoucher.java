package org.prgrms.vouchermission;

import java.time.LocalDate;

public class PercentDiscountVoucher implements Voucher {

    private final long voucherId;
    private final long percent;
    private final LocalDate createdDate;
    private final LocalDate expirationDate;
    private static final VoucherFactory voucherType = VoucherFactory.PERCENT;

    public PercentDiscountVoucher(long voucherId, long percent, LocalDate createdDate, LocalDate expirationDate) {
        this.voucherId = voucherId;
        this.percent = percent;
        this.createdDate = createdDate;
        this.expirationDate = expirationDate;
    }

    @Override
    public long getVoucherId() {
        return voucherId;
    }

    @Override
    public long discountAmount(long beforeDiscount) {
        return beforeDiscount * (100 - percent);
    }

    @Override
    public Voucher injectVoucherId(long realVoucherId) {
        if (realVoucherId <= 0) {
            throw new IllegalArgumentException("바우처 ID는 0 또는 음수일 수 없습니다.");
        }

        if (this.voucherId <= 0) {
            return new PercentDiscountVoucher(voucherId, this.percent, this.createdDate, this.expirationDate);
        } else {
            return this;
        }
    }
}
