package org.prgrms.vouchermission;

import java.time.LocalDate;

public class FixedAmountVoucher implements Voucher {

    private final long voucherId;
    private final long amount;
    private final LocalDate createdDate;
    private final LocalDate expirationDate;
    private static final VoucherFactory voucherType = VoucherFactory.AMOUNT;

    public FixedAmountVoucher(long voucherId, long amount, LocalDate createdDate, LocalDate expirationDate) {
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
