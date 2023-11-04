package com.prgrms.voucher_manage.domain.voucher.controller.dto;

import com.prgrms.voucher_manage.domain.voucher.entity.VoucherType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record CreateVoucherReq(
    @NotBlank(message = "바우처 유형을 입력해주세요.")
    VoucherType type,
    @NotNull(message="할인 금액을 입력해주세요.")
    Long discountAmount
){}
