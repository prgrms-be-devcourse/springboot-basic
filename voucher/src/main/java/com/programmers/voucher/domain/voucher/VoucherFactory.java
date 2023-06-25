package com.programmers.voucher.domain.voucher;

import com.programmers.voucher.console.Console;
import com.programmers.voucher.stream.VoucherStream;

import java.util.UUID;

public class VoucherFactory {

    private final Console console;
    private final VoucherStream voucherStream;

    public VoucherFactory(Console console, VoucherStream voucherStream) {
        this.console = console;
        this.voucherStream = voucherStream;
    }

    public Voucher createVoucher(VoucherEnum voucherEnum) {
        Voucher voucher;
        voucher = (VoucherEnum.FIXED == voucherEnum) ? createFixedVoucher() : createPercentVoucher();
        voucherStream.save(voucher);
        return voucher;
    }

    private PercentDiscountVoucher createPercentVoucher() {
        Integer rate = console.getRate();
        validateRate(rate);
        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID().toString().substring(0, 7), rate);
        return percentDiscountVoucher;
    }

    private static void validateRate(Integer rate) {
        if (rate >= 100) {
            throw new IllegalArgumentException("rate cannot exceed 100 percent. Do you want FixedAmountVoucher?");
        }
    }

    private Voucher createFixedVoucher() {
        Integer amount = console.getAmount();
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID().toString().substring(0, 7), amount);
        return fixedAmountVoucher;
    }
}
