package com.programmers.voucher.domain;

import com.programmers.voucher.entity.VoucherEntity;
import com.programmers.voucher.dto.VoucherRequestDto;
import com.programmers.voucher.dto.VoucherResponseDto;

import java.time.LocalDateTime;

public class VoucherMapper {

    public static Voucher convertRequestDtoToDomain(VoucherRequestDto requestDto) {
        return new Voucher(requestDto.voucherId(), requestDto.discount(), LocalDateTime.now());
    }

    public static VoucherResponseDto convertDomainToResponseDto(Voucher voucher) {
        return new VoucherResponseDto(voucher.getVoucherId(), voucher.getDiscount(),
                voucher.getCreatedDate());
    }

    public static VoucherEntity convertDomainToEntity(Voucher voucher) {
        return new VoucherEntity(voucher.getVoucherId(), voucher.getDiscount(),
                voucher.getCreatedDate());
    }

    public static Voucher convertEntityToDomain(VoucherEntity voucherEntity) {
        return new Voucher(voucherEntity.getVoucherId(), voucherEntity.getDiscount(),
                voucherEntity.getCreatedDate());
    }
}
