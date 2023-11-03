package org.programmers.springorder.dto.voucher;

import org.programmers.springorder.model.voucher.VoucherType;

public class VoucherRequestDto {
    private long discountValue;
    private VoucherType voucherType;

    public VoucherRequestDto() {
    }

    public VoucherRequestDto(long discountValue, VoucherType voucherType) {
        this.discountValue = discountValue;
        this.voucherType = voucherType;
        validateDiscountRange();
    }

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

    private void validateDiscountRange() {
        if (discountValue < voucherType.getMinimumValue() || discountValue > voucherType.getMaximumValue()) {
            throw new IllegalArgumentException(String.format("잘못된 입력 값입니다. %d ~ %d 사이의 값을 입력해주세요.", voucherType.getMinimumValue(), voucherType.getMaximumValue()));
        }
    }
}
