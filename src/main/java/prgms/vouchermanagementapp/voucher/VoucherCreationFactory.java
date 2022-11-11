package prgms.vouchermanagementapp.voucher;

import prgms.vouchermanagementapp.model.Amount;
import prgms.vouchermanagementapp.model.Ratio;
import prgms.vouchermanagementapp.voucher.model.FixedAmountVoucher;
import prgms.vouchermanagementapp.voucher.model.PercentDiscountVoucher;
import prgms.vouchermanagementapp.voucher.model.Voucher;

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
