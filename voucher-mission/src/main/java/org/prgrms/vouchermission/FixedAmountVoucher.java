package org.prgrms.vouchermission;

import java.time.LocalDate;

public class FixedAmountVoucher implements Voucher {

    private final long voucherId;
    private final long amount;
    private final LocalDate createdDate;
    private final LocalDate expirationDate;
    private static final VoucherFactory voucherType = VoucherFactory.AMOUNT;

    public FixedAmountVoucher(long voucherId, long amount, LocalDate createdDate, LocalDate expirationDate) {
        if (amount <= 0) {
            throw new IllegalArgumentException("할인액은 0 또는 음수일 수 없습니다.");
        }
        if (createdDate.isAfter(expirationDate)) {
            throw new IllegalArgumentException("만료일은 발급일보다 빠를 수 없습니다.");
        }
        this.voucherId = voucherId;
        this.amount = amount;
        this.createdDate = createdDate;
        this.expirationDate = expirationDate;
    }

    @Override
    public long getVoucherId() {
        return voucherId;
    }

    @Override
    public long discountAmount(long originalAmount) {
        if (this.amount > originalAmount) {
            throw new IllegalArgumentException("바우처 할인 대상의 금액은 바우처의 할인액보다 커야합니다.");
        }
        return originalAmount - amount;
    }

    @Override
    public Voucher injectVoucherId(long realVoucherId) {
        if (realVoucherId <= 0) {
            throw new IllegalArgumentException("바우처 ID는 0 또는 음수일 수 없습니다.");
        }

        if (this.voucherId <= 0) {
            return new FixedAmountVoucher(voucherId, this.amount, this.createdDate, this.expirationDate);
        } else {
            return this;
        }
    }

}
