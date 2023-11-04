package com.prgrms.voucher_manage.domain.voucher.controller.dto;

import com.prgrms.voucher_manage.domain.voucher.entity.VoucherType;

import java.util.UUID;


public record UpdateVoucherReq(
        UUID id,
        VoucherType type,
        Long discountAmount

) {
}

