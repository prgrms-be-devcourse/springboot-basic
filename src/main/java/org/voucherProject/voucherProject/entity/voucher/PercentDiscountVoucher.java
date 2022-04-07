package org.voucherProject.voucherProject.entity.voucher;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.lang.Nullable;
import java.util.UUID;

@RequiredArgsConstructor
@Getter
@ToString(exclude = {"voucherId", "voucherType"})
public class PercentDiscountVoucher implements Voucher {

    private final UUID voucherId;
    private final long percent;
    @Nullable
    private final VoucherType voucherType;
    @Nullable
    private VoucherStatus voucherStatus = VoucherStatus.VALID;

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (1 - percent / 100);
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
