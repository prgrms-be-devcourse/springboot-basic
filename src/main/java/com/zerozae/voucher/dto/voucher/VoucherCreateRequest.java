package com.zerozae.voucher.dto.voucher;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

public record VoucherCreateRequest(@Min(value = 1, message = "할인 정보는 1이상의 숫자값을 입력해야 합니다.") long discount,
                                   @NotNull(message = "바우처의 타입은 필수 입력란입니다.") String voucherType) {

    public VoucherCreateRequest(long discount, String voucherType) {
        this.discount = discount;
        this.voucherType = voucherType;
    }
}
