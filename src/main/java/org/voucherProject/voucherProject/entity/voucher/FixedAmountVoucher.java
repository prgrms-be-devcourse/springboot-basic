package org.voucherProject.voucherProject.entity.voucher;

import lombok.Getter;
import lombok.ToString;
import org.springframework.lang.Nullable;
import java.util.UUID;

@Getter
@ToString(exclude = {"voucherId", "voucherType"})
public class FixedAmountVoucher implements Voucher {

    private final UUID voucherId;
    private final long amount;
    @Nullable
    private final VoucherType voucherType = VoucherType.FIXED;
    @Nullable
    private VoucherStatus voucherStatus = VoucherStatus.VALID;
    private final int MIN_DISCOUNT_AMOUNT = 0;
    private final int MAX_DISCOUNT_AMOUNT = 10000;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        if (amount < MIN_DISCOUNT_AMOUNT || amount > MAX_DISCOUNT_AMOUNT || amount == 0) {
            throw new IllegalArgumentException();
        }
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - amount;
    }

    @Override
    public long getHowMuch() {
        return this.amount;
    }

    @Override
    public VoucherStatus getVoucherStatus() {
        return this.voucherStatus;
    }

    @Override
    public void useVoucher() {
        this.voucherStatus = VoucherStatus.EXPIRED;
    }

    @Override
    public void cancelVoucher() {
        this.voucherStatus = VoucherStatus.VALID;
    }
}
