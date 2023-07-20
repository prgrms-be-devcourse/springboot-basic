package com.prgrms.spring.controller.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class VoucherResponseDto {
    private String voucherName;
    private String discount;

    public static VoucherResponseDto of(String voucherName, String discount) {
        return new VoucherResponseDto(voucherName, discount);
    }
}
