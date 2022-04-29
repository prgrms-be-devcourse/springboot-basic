package com.example.voucherproject.voucher.dto;

import com.example.voucherproject.voucher.model.Voucher;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class VoucherDtoMapper {

    public static Voucher asEntity(VoucherDTO.Create dto) {
        return new Voucher(UUID.randomUUID(), dto.getType(), dto.getAmount(),
                LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS),
                LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
    }


    public static VoucherDTO.Result asResultDto(Voucher voucher) {
        return VoucherDTO.Result.builder()
                .id(voucher.getId())
                .type(voucher.getType())
                .amount(voucher.getAmount())
                .createdAt(voucher.getCreatedAt())
                .build();
    }

}
