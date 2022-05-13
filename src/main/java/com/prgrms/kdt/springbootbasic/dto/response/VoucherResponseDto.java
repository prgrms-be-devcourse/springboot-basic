package com.prgrms.kdt.springbootbasic.dto.response;

import com.prgrms.kdt.springbootbasic.voucher.entity.Voucher;

import java.util.UUID;

public record VoucherResponseDto(UUID voucherId, long amount, String voucherType) {
    public static VoucherResponseDto voucherToDto(Voucher voucher){
        return new VoucherResponseDto(voucher.getVoucherId(), voucher.getDiscountAmount(), voucher.getVoucherType());
    }
}
