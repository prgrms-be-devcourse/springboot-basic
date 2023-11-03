package org.prgms.kdtspringweek1.thymeleafController;

import org.prgms.kdtspringweek1.exception.InputExceptionCode;
import org.prgms.kdtspringweek1.voucher.entity.FixedAmountVoucher;
import org.prgms.kdtspringweek1.voucher.entity.PercentDiscountVoucher;
import org.prgms.kdtspringweek1.voucher.entity.Voucher;
import org.prgms.kdtspringweek1.voucher.entity.VoucherType;

public class CreateVoucherRequestDto {
    private long discountValue;
    private VoucherType voucherType;

    public long getDiscountValue() {
        return discountValue;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public void setDiscountValue(long discountValue) {
        this.discountValue = discountValue;
    }

    public void setVoucherType(VoucherType voucherType) {
        this.voucherType = voucherType;
    }

    public Voucher toVoucher() {
        if (voucherType.equals(VoucherType.FIXED_AMOUNT)) {
            return FixedAmountVoucher.createWithAmount(discountValue);
        } else if (voucherType.equals(VoucherType.PERCENT_DISCOUNT)) {
            return PercentDiscountVoucher.createWithPercent(discountValue);
        }

        throw new IllegalArgumentException(InputExceptionCode.INVALID_VOUCHER_TYPE.getMessage());
    }
}
