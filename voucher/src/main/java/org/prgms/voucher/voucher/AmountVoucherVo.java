package org.prgms.voucher.voucher;

import java.util.Objects;

public class AmountVoucherVo {
    private final AmountVoucherCreationType amountVoucherCreationType;
    private final int discountAmount;

    public AmountVoucherVo(AmountVoucherCreationType amountVoucherCreationType, int discountAmount) {
        this.amountVoucherCreationType = amountVoucherCreationType;
        this.discountAmount = discountAmount;
    }

    public int getDiscountAmount() {
        return discountAmount;
    }

    public AmountVoucherCreationType getAmountVoucherCreationType() {
        return amountVoucherCreationType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AmountVoucherVo amountVoucherVo = (AmountVoucherVo) o;
        return discountAmount == amountVoucherVo.discountAmount && amountVoucherCreationType == amountVoucherVo.amountVoucherCreationType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amountVoucherCreationType, discountAmount);
    }
}
