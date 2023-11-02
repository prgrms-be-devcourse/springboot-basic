package com.programmers.springbootbasic.domain.voucher.presentation.dto;

import com.programmers.springbootbasic.domain.voucher.domain.VoucherType.VoucherType;
import com.programmers.springbootbasic.domain.voucher.domain.VoucherType.VoucherTypeEnum;
import com.programmers.springbootbasic.domain.voucher.domain.entity.Voucher;
import java.util.UUID;

public class CreateVoucherRequest {

    private final VoucherType voucherType;
    private final Integer benefitValue;

    private CreateVoucherRequest(VoucherType voucherType, Integer benefitValue) {
        this.voucherType = voucherType;
        this.benefitValue = benefitValue;
    }

    public static CreateVoucherRequest of(String stringVoucherType, Integer benefitValue) {
        VoucherTypeEnum voucherEnum = VoucherTypeEnum.of(stringVoucherType);
        VoucherType voucherType = voucherEnum.getVoucherType(benefitValue);
        return new CreateVoucherRequest(voucherType, benefitValue);
    }

    public Voucher toEntity(UUID id) {
        return new Voucher(id, voucherType, benefitValue);
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public Integer getBenefitValue() {
        return benefitValue;
    }
}
