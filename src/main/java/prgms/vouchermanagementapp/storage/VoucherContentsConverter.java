package prgms.vouchermanagementapp.storage;

import org.springframework.stereotype.Component;
import prgms.vouchermanagementapp.voucher.model.FixedAmountVoucher;
import prgms.vouchermanagementapp.voucher.model.PercentDiscountVoucher;
import prgms.vouchermanagementapp.voucher.model.Voucher;

@Component
public class VoucherContentsConverter {

    public String toContents(Voucher voucher, String messageFormat) {
        if (voucher instanceof FixedAmountVoucher) {
            return toDetailContents((FixedAmountVoucher) voucher, messageFormat);
        }
        return toDetailContents((PercentDiscountVoucher) voucher, messageFormat);
    }

    private String toDetailContents(FixedAmountVoucher voucher, String messageFormat) {
        String voucherType = voucher.getClass().getSimpleName();
        long amount = voucher.getFixedDiscountAmount().getAmount();
        return String.format(messageFormat, voucherType, amount);
    }

    private String toDetailContents(PercentDiscountVoucher voucher, String messageFormat) {
        String voucherType = voucher.getClass().getSimpleName();
        long ratio = voucher.getFixedDiscountRatio().getRatio();
        return String.format(messageFormat, voucherType, ratio + "%");
    }
}
