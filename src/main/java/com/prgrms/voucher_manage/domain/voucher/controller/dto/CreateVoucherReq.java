package com.prgrms.voucher_manage.domain.voucher.controller.dto;

import com.prgrms.voucher_manage.domain.voucher.entity.VoucherType;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public record CreateVoucherReq(
        @NotBlank(message = "바우처 유형을 입력해주세요.")
        VoucherType type,
        @Min(value = 1, message = "할인 범위가 올바르지 않습니다.")
        Long discountAmount
) {
}
