package com.prgrms.spring.controller.dto.request;

import com.prgrms.spring.domain.voucher.VoucherType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class VoucherCreateRequestDto {
    private VoucherType voucherType;
    private Long discount;

    public static VoucherCreateRequestDto of(VoucherType voucherType, Long discount) {
        return new VoucherCreateRequestDto(voucherType,discount);
    }
}
