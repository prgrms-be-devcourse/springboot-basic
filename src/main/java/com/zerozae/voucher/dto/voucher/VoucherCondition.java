package com.zerozae.voucher.dto.voucher;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import org.springframework.lang.Nullable;

public record VoucherCondition(@Nullable String voucherType,
                               @Nullable
                               @Min(value = 1, message = "할인 정보는 1이상의 숫자값을 입력해야 합니다.") Long discount,
                               @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "날짜 형식은 yyyy-MM-dd 이어야 합니다.") @Nullable String startCreatedAt,
                               @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "날짜 형식은 yyyy-MM-dd 이어야 합니다.") @Nullable String endCreatedAt) {

    public VoucherCondition(String voucherType, Long discount, String startCreatedAt , String endCreatedAt) {
        this.voucherType = voucherType;
        this.discount = discount;
        this.startCreatedAt = startCreatedAt;
        this.endCreatedAt = endCreatedAt;
    }
}
