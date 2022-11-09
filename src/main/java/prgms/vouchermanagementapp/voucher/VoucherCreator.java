package prgms.vouchermanagementapp.voucher;

import prgms.vouchermanagementapp.model.Amount;
import prgms.vouchermanagementapp.voucher.model.FixedAmountVoucher;

import java.util.UUID;

public class VoucherCreator {

    public FixedAmountVoucher createFixedAmountVoucher(Amount fixedDiscountAmount) {
        UUID voucherId = UUID.randomUUID();
        return new FixedAmountVoucher(voucherId, fixedDiscountAmount);
    }
}
