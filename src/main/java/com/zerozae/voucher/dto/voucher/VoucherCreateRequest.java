package com.zerozae.voucher.dto.voucher;

import com.zerozae.voucher.domain.voucher.VoucherType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class VoucherCreateRequest {

    @NotNull(message = "할인 정보는 필수 입력란입니다.")
    private long discount;

    @NotNull(message = "바우처의 타입은 필수 입력란입니다.")
    private String voucherType;

    public VoucherCreateRequest(long discount, String voucherType) {
        this.discount = discount;
        this.voucherType = voucherType;
    }
}
