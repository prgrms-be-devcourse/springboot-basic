package org.programmers.springboot.basic.domain.voucher.mapper;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.programmers.springboot.basic.domain.voucher.dto.VoucherRequestDto;
import org.programmers.springboot.basic.domain.voucher.dto.VoucherResponseDto;
import org.programmers.springboot.basic.domain.voucher.entity.Voucher;
import org.programmers.springboot.basic.domain.voucher.entity.VoucherType;
import org.programmers.springboot.basic.util.generator.UUIDGenerator;

import java.util.UUID;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VoucherEntityMapper {

    VoucherEntityMapper INSTANCE = Mappers.getMapper(VoucherEntityMapper.class);

    @Mapping(target = "voucherId", source = "voucherId")
    @Mapping(target = "voucherType", source = "voucherType")
    @Mapping(target = "discount", source = "discount")
    VoucherRequestDto mapToDtoWithAllArgs(UUID voucherId, VoucherType voucherType, Long discount);

    @Mapping(target = "voucherType", source = "voucherType")
    @Mapping(target = "discount", source = "discount")
    VoucherRequestDto entityToDtoWithDetails(VoucherType voucherType, Long discount);

    @Mapping(target = "voucherId", source = "voucherId")
    @Mapping(target = "discount", source = "discount")
    VoucherRequestDto entityToDtoWithDiscount(String voucherId, Long discount);

    VoucherResponseDto entityToDto(Voucher voucher);

    @Mapping(target = "voucherId", source = "voucherId")
    VoucherRequestDto entityToDtoWithUUID(String voucherId);

    Voucher mapToVoucher(VoucherRequestDto voucherRequestDto);

    @Mapping(target = "voucherId", expression = "java(mapToVoucherId(uuidGenerator))")
    Voucher mapToVoucherWithGenerator(VoucherRequestDto voucherRequestDto, @Context UUIDGenerator uuidGenerator);

    default UUID mapToVoucherId(@Context UUIDGenerator uuidGenerator) {
        return uuidGenerator.generateUUID();
    }

    default UUID map(String value) {
        return UUID.fromString(value);
    }
}
