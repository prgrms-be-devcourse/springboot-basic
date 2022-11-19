package prgms.vouchermanagementapp.voucher;

import prgms.vouchermanagementapp.domain.FixedAmountVoucher;
import prgms.vouchermanagementapp.domain.PercentDiscountVoucher;
import prgms.vouchermanagementapp.domain.Voucher;
import prgms.vouchermanagementapp.domain.model.Amount;
import prgms.vouchermanagementapp.domain.model.Ratio;

import java.util.UUID;

public class VoucherCreationFactory {

    private VoucherCreationFactory() {
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
