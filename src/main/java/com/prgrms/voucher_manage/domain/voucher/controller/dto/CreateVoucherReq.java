package com.prgrms.voucher_manage.domain.voucher.controller.dto;

import com.prgrms.voucher_manage.domain.voucher.entity.VoucherType;

import javax.validation.constraints.Min;

public record CreateVoucherReq(
        VoucherType type,
        @Min(value = 1, message = "할인 범위가 올바르지 않습니다.")
        Long discountAmount
) {
}
