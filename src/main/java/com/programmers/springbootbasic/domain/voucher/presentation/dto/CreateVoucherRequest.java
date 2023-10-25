package com.programmers.springbootbasic.domain.voucher.presentation.dto;

import com.programmers.springbootbasic.domain.voucher.domain.VoucherType.VoucherTypeEnum;
import com.programmers.springbootbasic.domain.voucher.domain.entity.Voucher;
import java.util.UUID;

public class CreateVoucherRequest {
    private final VoucherTypeEnum voucherTypeEnum;
    private final Integer benefitValue;

    private CreateVoucherRequest(VoucherTypeEnum voucherTypeEnum, Integer benefitValue) {
        this.voucherTypeEnum = voucherTypeEnum;
        this.benefitValue = benefitValue;
    }

    public static CreateVoucherRequest from(String voucherType, Integer benefitValue) {
        VoucherTypeEnum voucherEnum = VoucherTypeEnum.of(voucherType);
        return new CreateVoucherRequest(voucherEnum, benefitValue);
    }

    public Voucher toEntity(UUID id) {
        return new Voucher(id,voucherTypeEnum.getVoucherType(benefitValue), benefitValue);
    }

    public VoucherTypeEnum getVoucherType() {
        return voucherTypeEnum;
    }

    public Integer getBenefitValue() {
        return benefitValue;
    }
}
