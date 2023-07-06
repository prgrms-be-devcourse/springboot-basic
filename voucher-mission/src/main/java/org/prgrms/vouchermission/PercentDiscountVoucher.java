package org.prgrms.vouchermission;

import java.time.LocalDate;

public class PercentDiscountVoucher extends Voucher {

    private final long percent;

    public PercentDiscountVoucher(long voucherId, long percent, LocalDate createdDate, LocalDate expirationDate) {
        super(voucherId, createdDate, expirationDate, VoucherFactory.PERCENT);
        validateVoucherValue(percent);
        validateVoucherPeriod(createdDate, expirationDate);
        this.percent = percent;
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

        if (percent >= 100) {
            throw new IllegalArgumentException("할인율은 100 % 이상일 수 없습니다.");
        }
    }

    @Override
    public long discountAmount(long originalAmount) {
        return (long) (originalAmount * (1 - percent / 100.0));
    }

    @Override
    long getValue() {
        return this.percent;
    }
}
