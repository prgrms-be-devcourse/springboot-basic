package org.prgms.kdtspringweek1.console;

import org.prgms.kdtspringweek1.voucher.entity.FixedAmountVoucher;
import org.prgms.kdtspringweek1.voucher.entity.PercentDiscountVoucher;
import org.prgms.kdtspringweek1.voucher.entity.Voucher;

public class CreateVoucherRequestDto {
    private final long discountValue;

    public CreateVoucherRequestDto(long discountValue) {
        this.discountValue = discountValue;
    }

    public Voucher toFixedAmountVoucher() {
        return FixedAmountVoucher.createWithAmount(discountValue);
    }

    public Voucher toPercentDiscountVoucher() {
        return PercentDiscountVoucher.createWithPercent(discountValue);
    }
}
