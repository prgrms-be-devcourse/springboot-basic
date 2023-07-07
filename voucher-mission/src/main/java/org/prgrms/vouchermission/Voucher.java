package org.prgrms.vouchermission;

import java.time.LocalDate;

public abstract class Voucher {

    private long voucherId;
    private final LocalDate createdDate;
    private final LocalDate expirationDate;
    private final VoucherFactory voucherType;

    abstract long discountAmount(long originalAmount);

    abstract long getValue();

    public Voucher(LocalDate createdDate, LocalDate expirationDate, VoucherFactory voucherType) {
        this.createdDate = createdDate;
        this.expirationDate = expirationDate;
        this.voucherType = voucherType;
    }

    public long getVoucherId() {
        return voucherId;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public VoucherFactory getVoucherType() {
        return voucherType;
    }
}
