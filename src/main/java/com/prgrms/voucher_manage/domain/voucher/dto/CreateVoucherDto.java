package com.prgrms.voucher_manage.domain.voucher.dto;

import com.prgrms.voucher_manage.console.VoucherType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor @Getter
public class CreateVoucherDto {
    private final VoucherType voucherType;
    private final Long discountAmount;
}
