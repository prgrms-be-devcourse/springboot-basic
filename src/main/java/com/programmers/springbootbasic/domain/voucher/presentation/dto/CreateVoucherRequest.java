package com.programmers.springbootbasic.domain.voucher.presentation.dto;

import com.programmers.springbootbasic.domain.voucher.domain.VoucherType.VoucherType;
import com.programmers.springbootbasic.domain.voucher.domain.VoucherType.VoucherTypeEnum;
import com.programmers.springbootbasic.domain.voucher.domain.entity.Voucher;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

public class CreateVoucherRequest {

    @NotNull
    private VoucherType voucherType;
    @NotNull
    private Integer benefitValue;

    private CreateVoucherRequest(VoucherTypeEnum voucherType, Integer benefitValue) {
        this.voucherType = voucherType.getVoucherType(benefitValue);
        this.benefitValue = benefitValue;
    }

    // TODO: String 으로 받을지 Enum 으로 받을지는 고민해보자.
    public static CreateVoucherRequest of(VoucherTypeEnum stringVoucherType, Integer benefitValue) {
        return new CreateVoucherRequest(stringVoucherType, benefitValue);
    }

    public Voucher toEntity(UUID id, LocalDateTime createdAt) {
        return new Voucher(id, voucherType, benefitValue, createdAt);
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public Integer getBenefitValue() {
        return benefitValue;
    }
}
