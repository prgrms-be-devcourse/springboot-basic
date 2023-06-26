package com.programmers.voucher.domain;

import com.programmers.voucher.dto.VoucherRequestDto;
import com.programmers.voucher.dto.VoucherResponseDto;

import java.time.LocalDate;
import java.util.UUID;

public class VoucherMapper {

    public static VoucherResponseDto domainToResponseDto(Voucher voucher) {
        return new VoucherResponseDto(voucher.getVoucherId(), voucher.getDiscount(),
                voucher.getCreatedDate());
    }

    public static Voucher requestDtoToDomain(VoucherRequestDto requestDto) {
        return new Voucher(UUID.randomUUID(), requestDto.discount(), LocalDate.now());
    }

    public static VoucherEntity domainToEntity(Voucher voucher) {
        return new VoucherEntity(voucher.getVoucherId(), voucher.getDiscount(),
                voucher.getCreatedDate());
    }

    public static Voucher entityToDomain(VoucherEntity voucherEntity) {
        return new Voucher(voucherEntity.getVoucherId(), voucherEntity.getDiscount(),
                voucherEntity.getCreatedDate());
    }
}
