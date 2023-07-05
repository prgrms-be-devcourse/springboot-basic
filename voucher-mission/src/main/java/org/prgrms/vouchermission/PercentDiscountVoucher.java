package org.prgrms.vouchermission;

import java.time.LocalDate;

public class PercentDiscountVoucher implements Voucher {

    private final long voucherId;
    private final long percent;
    private final LocalDate createdDate;
    private final LocalDate expirationDate;
    private static final VoucherFactory voucherType = VoucherFactory.PERCENT;

    public PercentDiscountVoucher(long voucherId, long percent, LocalDate createdDate, LocalDate expirationDate) {
        validateVoucherValue(percent);
        validateVoucherPeriod(createdDate, expirationDate);
        this.voucherId = voucherId;
        this.percent = percent;
        this.createdDate = createdDate;
        this.expirationDate = expirationDate;
    }

    private static void validateVoucherPeriod(LocalDate createdDate, LocalDate expirationDate) {
        if (createdDate.isAfter(expirationDate)) {
            throw new IllegalArgumentException("만료일은 발급일보다 빠를 수 없습니다.");
        }
    }

    private static void validateVoucherValue(long percent) {
        if (percent <= 0) {
            throw new IllegalArgumentException("할인율은 0 또는 음수일 수 없습니다.");
        }
    }

    @Override
    public long getVoucherId() {
        return voucherId;
    }

    @Override
    public long discountAmount(long originalAmount) {
        return (long) (originalAmount * (1 - percent / 100.0));
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
