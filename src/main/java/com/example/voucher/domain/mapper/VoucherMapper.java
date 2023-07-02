package com.example.voucher.domain.mapper;

import com.example.voucher.domain.Voucher;
import com.example.voucher.domain.dto.VoucherDto;

public class VoucherMapper {
    public static VoucherDto toDto(Voucher voucher) {
        return new VoucherDto(voucher.getVoucherId(), voucher.getAmount());
    }

    public static Voucher fromDto(VoucherDto voucherDto) {
        return new Voucher(voucherDto.getVoucherId(), voucherDto.getAmount());
    }
}