package com.programmers.springbootbasic.mediator.dto;

import com.programmers.springbootbasic.domain.voucher.domain.VoucherType.VoucherTypeEnum;
import com.programmers.springbootbasic.domain.voucher.presentation.dto.UpdateVoucherRequest;
import java.util.UUID;

public class UpdateVoucherMediatorRequest {

    private final UUID id;
    private final VoucherTypeEnum voucherType;
    private final Integer benefitValue;

    public UpdateVoucherMediatorRequest(
        UUID id, VoucherTypeEnum voucherType, Integer benefitValue
    ) {
        this.id = id;
        this.voucherType = voucherType;
        this.benefitValue = benefitValue;
    }

    public static UpdateVoucherMediatorRequest of(
        UUID id, String voucherType, Integer benefitValue
    ) {
        return new UpdateVoucherMediatorRequest(id, VoucherTypeEnum.of(voucherType), benefitValue);
    }

    public UpdateVoucherRequest toUpdateVoucherRequest() {
        return new UpdateVoucherRequest(voucherType, benefitValue);
    }

    public UUID getId() {
        return id;
    }

    public VoucherTypeEnum getVoucherType() {
        return voucherType;
    }

    public Integer getBenefitValue() {
        return benefitValue;
    }
}
