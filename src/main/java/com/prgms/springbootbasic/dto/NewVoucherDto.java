package com.prgms.springbootbasic.dto;

import com.prgms.springbootbasic.domain.Voucher;
import com.prgms.springbootbasic.util.VoucherType;

public record NewVoucherDto(
    String type,
    Long amount
) {

    public Voucher toEntity() {
        VoucherType voucherType = VoucherType.of(type);
        return voucherType.createNewVoucher(amount);
    }

}
