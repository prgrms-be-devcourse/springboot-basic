package com.programmers.springweekly.dto.voucher.request;

import com.programmers.springweekly.domain.voucher.VoucherType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VoucherCreateRequest {

    @NotBlank
    @Positive
    private long discountAmount;

    @NotNull
    private VoucherType voucherType;

    @Builder
    public VoucherCreateRequest(long discountAmount, VoucherType voucherType) {
        this.discountAmount = discountAmount;
        this.voucherType = voucherType;
    }

}
