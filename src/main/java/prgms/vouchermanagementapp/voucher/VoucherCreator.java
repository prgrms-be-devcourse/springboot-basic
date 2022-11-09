package prgms.vouchermanagementapp.voucher;

import prgms.vouchermanagementapp.model.Amount;
import prgms.vouchermanagementapp.model.Ratio;
import prgms.vouchermanagementapp.voucher.model.FixedAmountVoucher;
import prgms.vouchermanagementapp.voucher.model.PercentDiscountVoucher;

import java.util.UUID;

public class VoucherCreator {

    public FixedAmountVoucher createFixedAmountVoucher(Amount fixedDiscountAmount) {
        UUID voucherId = UUID.randomUUID();
        return new FixedAmountVoucher(voucherId, fixedDiscountAmount);
    }

    public PercentDiscountVoucher createPercentDiscountVoucher(Ratio fixedDiscountRatio) {
        UUID voucherId = UUID.randomUUID();
        return new PercentDiscountVoucher(voucherId, fixedDiscountRatio);
    }
}
