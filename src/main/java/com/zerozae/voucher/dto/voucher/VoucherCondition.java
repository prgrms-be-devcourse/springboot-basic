package com.zerozae.voucher.dto.voucher;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import org.springframework.lang.Nullable;

@Getter
public class VoucherCondition {

    @Nullable
    private String voucherType;

    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "날짜 형식은 yyyy-MM-dd 이어야 합니다.")
    private String createdAt;

    public VoucherCondition(String voucherType, String createdAt) {
        this.voucherType = voucherType;
        this.createdAt = createdAt;
    }
}
