package org.prgrms.vouchermission;

import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

    private final UUID voucherId;
    private final long amount;
    private final LocalDate createdDate;
    private final LocalDate expirationDate;

    private FixedAmountVoucher(long amount, LocalDate createdDate, LocalDate expirationDate) {
        this.voucherId = UUID.randomUUID();
        this.amount = amount;
        this.createdDate = createdDate;
        this.expirationDate = expirationDate;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discountAmount(long beforeDiscount) {
        return amount;
    }

    public long getAmount() { return amount;}

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public static Voucher createPercentDiscountVoucher(long amount, LocalDate createdDate, LocalDate expirationDate) {
        return new FixedAmountVoucher(amount, createdDate, expirationDate);
    }

}
