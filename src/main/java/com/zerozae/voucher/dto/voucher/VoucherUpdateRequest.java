package com.zerozae.voucher.dto.voucher;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

public record VoucherUpdateRequest(@Pattern(regexp = "\\d+", message = "숫자 형식이어야 합니다.")
                                   @NotNull(message = "할인 정보는 필수 입력란입니다.")  Long discount,
                                   @NotNull(message = "바우처의 타입은 필수 입력란입니다.") String useStatusType) {

    public VoucherUpdateRequest(Long discount, String useStatusType) {
        this.discount = discount;
        this.useStatusType = useStatusType;
    }
}
