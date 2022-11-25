package prgms.vouchermanagementapp.service;

import prgms.vouchermanagementapp.domain.FixedAmountVoucher;
import prgms.vouchermanagementapp.domain.PercentDiscountVoucher;
import prgms.vouchermanagementapp.domain.Voucher;
import prgms.vouchermanagementapp.domain.value.Amount;
import prgms.vouchermanagementapp.domain.value.Ratio;

import java.util.UUID;

public class VoucherFactory {

    private VoucherFactory() {
    }

    public static Voucher createVoucher(Amount fixedDiscountAmount) {
        UUID voucherId = UUID.randomUUID();
        return new FixedAmountVoucher(voucherId, fixedDiscountAmount);
    }

    public static Voucher createVoucher(Ratio fixedDiscountRatio) {
        UUID voucherId = UUID.randomUUID();
        return new PercentDiscountVoucher(voucherId, fixedDiscountRatio);
    }
}
