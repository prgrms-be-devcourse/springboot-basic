package prgms.vouchermanagementapp.repository.util;

import prgms.vouchermanagementapp.domain.FixedAmountVoucher;
import prgms.vouchermanagementapp.domain.PercentDiscountVoucher;
import prgms.vouchermanagementapp.domain.Voucher;

public class VoucherContentsConverter {

    private VoucherContentsConverter() {
    }

    public static String toContents(Voucher voucher, String messageFormat) {
        if (voucher instanceof FixedAmountVoucher) {
            return toDetailContents((FixedAmountVoucher) voucher, messageFormat);
        }
        return toDetailContents((PercentDiscountVoucher) voucher, messageFormat);
    }

    private static String toDetailContents(FixedAmountVoucher voucher, String messageFormat) {
        String voucherType = voucher.getClass().getSimpleName();
        long amount = voucher.getFixedDiscountAmount().getAmount();
        return String.format(messageFormat, voucherType, amount);
    }

    private static String toDetailContents(PercentDiscountVoucher voucher, String messageFormat) {
        String voucherType = voucher.getClass().getSimpleName();
        long ratio = voucher.getFixedDiscountRatio().getRatio();
        return String.format(messageFormat, voucherType, ratio + "%");
    }
}
