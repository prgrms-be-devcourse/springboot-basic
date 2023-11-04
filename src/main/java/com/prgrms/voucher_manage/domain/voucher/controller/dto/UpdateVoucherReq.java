package com.prgrms.voucher_manage.domain.voucher.controller.dto;

import com.prgrms.voucher_manage.domain.voucher.entity.VoucherType;


public record UpdateVoucherReq(
        VoucherType type,
        Long discountAmount

) {
}

