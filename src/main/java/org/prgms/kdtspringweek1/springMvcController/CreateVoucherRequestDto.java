package org.prgms.kdtspringweek1.springMvcController;

import org.prgms.kdtspringweek1.exception.InputExceptionCode;
import org.prgms.kdtspringweek1.voucher.entity.FixedAmountVoucher;
import org.prgms.kdtspringweek1.voucher.entity.PercentDiscountVoucher;
import org.prgms.kdtspringweek1.voucher.entity.Voucher;
import org.prgms.kdtspringweek1.voucher.entity.VoucherType;

public class CreateVoucherRequestDto {
    private long discountValue;
    private String voucherType;

    public long getDiscountValue() {
        return discountValue;
    }

    public String getVoucherType() {
        return voucherType;
    }

    public void setDiscountValue(long discountValue) {
        this.discountValue = discountValue;
    }

    public void setVoucherType(String voucherType) {
        this.voucherType = voucherType;
    }

    public Voucher toVoucher() {
        if (voucherType.equalsIgnoreCase(VoucherType.FIXED_AMOUNT.getName())) {
            return FixedAmountVoucher.createWithAmount(discountValue);
        } else if (voucherType.equalsIgnoreCase(VoucherType.PERCENT_DISCOUNT.getName())) {
            return PercentDiscountVoucher.createWithPercent(discountValue);
        }

        throw new IllegalArgumentException(InputExceptionCode.INVALID_VOUCHER_TYPE.getMessage());
    }
}
