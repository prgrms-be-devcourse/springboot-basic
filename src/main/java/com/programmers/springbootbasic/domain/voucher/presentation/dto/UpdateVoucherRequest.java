package com.programmers.springbootbasic.domain.voucher.presentation.dto;

import com.programmers.springbootbasic.domain.voucher.domain.VoucherType.VoucherType;
import com.programmers.springbootbasic.domain.voucher.domain.VoucherType.VoucherTypeEnum;
import jakarta.annotation.Nullable;

public class UpdateVoucherRequest {
    @Nullable
    private final VoucherType voucherType;
    @Nullable
    private final Integer benefitValue;

    public UpdateVoucherRequest(VoucherTypeEnum voucherType, Integer benefitValue) {
        this.voucherType = voucherType.getVoucherType(benefitValue);
        this.benefitValue = benefitValue;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public Integer getBenefitValue() {
        return benefitValue;
    }

}
