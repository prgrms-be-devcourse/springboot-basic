package org.programmers.program.voucher.validator;

import org.programmers.program.voucher.model.FixedAmountVoucher;
import org.programmers.program.voucher.model.Voucher;
import org.programmers.program.voucher.model.VoucherType;

import java.time.LocalDate;
import java.util.function.Predicate;

import static org.programmers.program.voucher.model.VoucherType.*;


public class VoucherValidator implements Predicate<Voucher> {
    @Override
    public boolean test(Voucher voucher) {
        if(voucher.getExpirationDate().isBefore(LocalDate.now()))
            return false;

        VoucherType voucherType;
        if (voucher instanceof FixedAmountVoucher)
            voucherType = FIXED;
        else
            voucherType = PERCENT;

        if (voucher.getDiscountAmount() < voucherType.getLowerBound()
                || voucher.getDiscountAmount() > voucherType.getUpperBound())
            return false;
        return true;
    }
}