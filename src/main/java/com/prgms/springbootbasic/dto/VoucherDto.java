package com.prgms.springbootbasic.dto;

import com.prgms.springbootbasic.domain.Voucher;
import com.prgms.springbootbasic.util.VoucherType;

import java.util.UUID;

public record VoucherDto(
    UUID voucherId,
    String type,
    Long amount
) {

    public static VoucherDto from(Voucher voucher) {
        VoucherType voucherType = voucher.getVoucherType();
        return new VoucherDto(voucher.getVoucherId(), voucherType.getType(), voucher.getAmount());
    }

}
