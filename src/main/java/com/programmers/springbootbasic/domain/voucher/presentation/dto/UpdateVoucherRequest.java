package com.programmers.springbootbasic.domain.voucher.presentation.dto;

import com.programmers.springbootbasic.domain.voucher.domain.VoucherType.VoucherType;
import com.programmers.springbootbasic.domain.voucher.domain.VoucherType.VoucherTypeEnum;

public class UpdateVoucherRequest {

    private final VoucherType voucherType;
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
