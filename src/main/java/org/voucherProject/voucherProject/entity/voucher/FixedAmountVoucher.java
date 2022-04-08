package org.voucherProject.voucherProject.entity.voucher;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.lang.Nullable;
import java.util.UUID;

@RequiredArgsConstructor
@Getter
@ToString(exclude = {"voucherId", "voucherType"})
public class FixedAmountVoucher implements Voucher {

    private final UUID voucherId;
    private final long amount;
    @Nullable
    private final VoucherType voucherType = VoucherType.FIXED;
    @Nullable
    private VoucherStatus voucherStatus = VoucherStatus.VALID;

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
