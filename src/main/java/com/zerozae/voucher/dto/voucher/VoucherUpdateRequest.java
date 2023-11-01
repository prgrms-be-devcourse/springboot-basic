package com.zerozae.voucher.dto.voucher;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class VoucherUpdateRequest {

    @Min(value = 1, message = "할인 정보는 필수 입력란이며 1이상의 값을 입력해주세요.")
    private long discount;

    @NotNull(message = "바우처의 타입은 필수 입력란입니다.")
    private String useStatusType;

    public VoucherUpdateRequest(long discount, String useStatusType) {
        this.discount = discount;
        this.useStatusType = useStatusType;
    }
}
