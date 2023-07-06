package org.prgrms.vouchermission;

import java.time.LocalDate;

public class FixedAmountVoucher extends Voucher {

    private final long amount;

    public FixedAmountVoucher(long voucherId, long amount, LocalDate createdDate, LocalDate expirationDate) {
        super(voucherId, createdDate, expirationDate, VoucherFactory.AMOUNT);
        validateVoucherValue(amount);
        validateVoucherPeriod(createdDate, expirationDate);
        this.amount = amount;
    }

    private static void validateVoucherPeriod(LocalDate createdDate, LocalDate expirationDate) {
        if (createdDate.isAfter(expirationDate)) {
            throw new IllegalArgumentException("만료일은 발급일보다 빠를 수 없습니다.");
        }
    }

    private static void validateVoucherValue(long amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("할인액은 0 또는 음수일 수 없습니다.");
        }
    }

    @Override
    public long discountAmount(long originalAmount) {
        return (originalAmount - amount) > 0 ? originalAmount - amount : 0;
    }

    @Override
    long getValue() {
        return this.amount;
    }
}
