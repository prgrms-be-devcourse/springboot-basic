package org.prgrms.springbootbasic.mapper;

import org.prgrms.springbootbasic.dto.VoucherInputDto;
import org.prgrms.springbootbasic.entity.Voucher;
import org.prgrms.springbootbasic.type.VoucherType;

import java.time.LocalDate;
import java.util.UUID;


public class VoucherDtoMapper {
    public static Voucher VoucherInputDtoToVoucher(VoucherInputDto voucherInputDto) {
        return Voucher.builder()
                .voucherId(UUID.randomUUID())
                .voucherType(VoucherType.valueOf(voucherInputDto.getVoucherType()))
                .discountQuantity(voucherInputDto.getDiscountQuantity())
                .discountRatio(voucherInputDto.getDiscountRatio())
                .voucherCount(voucherInputDto.getVoucherCount())
                .createdAt(LocalDate.parse(voucherInputDto.getCreatedAt()))
                .endedAt(LocalDate.parse(voucherInputDto.getEndedAt()))
                .build();
    }
}
