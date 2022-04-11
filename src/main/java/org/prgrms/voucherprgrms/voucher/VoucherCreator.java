package org.prgrms.voucherprgrms.voucher;

import org.prgrms.voucherprgrms.voucher.model.FixedAmountVoucher;
import org.prgrms.voucherprgrms.voucher.model.PercentDiscountVoucher;
import org.prgrms.voucherprgrms.voucher.model.Voucher;
import org.prgrms.voucherprgrms.voucher.model.VoucherType;
import org.prgrms.voucherprgrms.io.InputConsole;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class VoucherCreator {
    private final InputConsole inputConsole;

    public VoucherCreator(InputConsole inputConsole) {
        this.inputConsole = inputConsole;
    }

    public Voucher create() {
        VoucherType type = VoucherType.getType(inputConsole.getVoucherType());

        UUID voucherId = UUID.randomUUID();
        switch (type) {
            case FIXEDAMOUNT:
                return getFixedAmountVoucher(voucherId);
            case PERCENTDISCOUNT:
                return getPercentDiscountVoucher(voucherId);
            default:
                //Exception
                throw new IllegalArgumentException();
        }
    }

    private Voucher getPercentDiscountVoucher(UUID voucherId) {
        long percent = inputConsole.getVoucherValue("Type **percent** to create PercentDiscountVoucher");
        return new PercentDiscountVoucher(voucherId, percent);
    }

    private Voucher getFixedAmountVoucher(UUID voucherId) {
        long amount = inputConsole.getVoucherValue("Type **amount** to create FixedAmountVoucher");
        return new FixedAmountVoucher(voucherId, amount);
    }
}
