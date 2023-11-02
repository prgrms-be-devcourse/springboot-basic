package com.zerozae.voucher.dto.voucher;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public record VoucherCreateRequest(@Min(value = 1, message = "할인 정보는 필수 입력란이며 1이상의 값을 입력해주세요.") long discount,
                                   @NotNull(message = "바우처의 타입은 필수 입력란입니다.") String voucherType) {

    public VoucherCreateRequest(long discount, String voucherType) {
        this.discount = discount;
        this.voucherType = voucherType;
    }
}
