package org.prgrms.voucherprgrms.voucher;

import org.prgrms.voucherprgrms.voucher.entity.FixedAmountVoucher;
import org.prgrms.voucherprgrms.voucher.entity.PercentDiscountVoucher;
import org.prgrms.voucherprgrms.voucher.entity.Voucher;
import org.prgrms.voucherprgrms.voucher.entity.VoucherType;
import org.prgrms.voucherprgrms.io.InputConsole;

import java.util.UUID;

public class VoucherCreator {
    private final InputConsole inputConsole;

    public VoucherCreator(InputConsole inputConsole) {
        this.inputConsole = inputConsole;
    }

    public Voucher create(VoucherType type) {
        UUID voucherId = UUID.randomUUID();
        switch (type) {
            case FIXEDAMOUNT:
                return getFixedAmountVoucher(voucherId);
            case PERCENTDISCOUNT:
                return getPercentDiscountVoucher(voucherId);
            default:
                //Exception
                return null;
        }
    }

    private Voucher getPercentDiscountVoucher(UUID voucherId) {
        long percent = inputConsole.getValue("Type **percent** to create PercentDiscountVoucher");
        return new PercentDiscountVoucher(voucherId, percent);
    }

    private Voucher getFixedAmountVoucher(UUID voucherId) {
        long amount = inputConsole.getValue("Type **amount** to create FixedAmountVoucher");
        return new FixedAmountVoucher(voucherId, amount);
    }

}
