package com.programmers.voucher.domain;

import com.programmers.voucher.dto.ServiceDto;
import com.programmers.voucher.dto.VoucherCreateRequest;
import com.programmers.voucher.dto.VoucherRequestDto;
import com.programmers.voucher.dto.VoucherResponseDto;
import com.programmers.voucher.entity.VoucherEntity;

import java.time.LocalDateTime;
import java.util.UUID;

public class VoucherMapper {

    private VoucherMapper() {
    }

    public static Voucher convertRequestDtoToDomain(VoucherRequestDto requestDto) {
        return new Voucher(requestDto.voucherId(), requestDto.discount(), LocalDateTime.now());
    }

    public static VoucherResponseDto convertDomainToResponseDto(Voucher voucher) {
        return new VoucherResponseDto(voucher.getVoucherId(),
                voucher.getDiscount(),
                voucher.getCreatedAt(),
                voucher.getExpiredAt());
    }

    public static VoucherEntity convertDomainToEntity(Voucher voucher) {
        return new VoucherEntity(voucher.getVoucherId(), voucher.getDiscount(),
                voucher.getCreatedAt());
    }

    public static Voucher convertEntityToDomain(VoucherEntity voucherEntity) {
        return new Voucher(voucherEntity.getVoucherId(), voucherEntity.getDiscount(),
                voucherEntity.getCreatedDate());
    }

    public static ServiceDto convertCreateToServiceDto(VoucherCreateRequest createRequest) {
        Discount discount = Discount.of(createRequest.discountType(), createRequest.value());
        return new ServiceDto(UUID.randomUUID(), discount);
    }

    public static ServiceDto convertRequestDtoToServiceDto(VoucherRequestDto requestDto) {
        return new ServiceDto(requestDto.voucherId(), requestDto.discount());
    }

    public static Voucher convertServiceToDomain(ServiceDto serviceDto) {
        return new Voucher(serviceDto.voucherId(), serviceDto.discount(), LocalDateTime.now());
    }
}
