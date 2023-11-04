package org.programmers.springboot.basic.domain.voucher.mapper;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.programmers.springboot.basic.domain.voucher.dto.VoucherControllerRequestDto;
import org.programmers.springboot.basic.domain.voucher.dto.VoucherRequestDto;
import org.programmers.springboot.basic.domain.voucher.dto.VoucherResponseDto;
import org.programmers.springboot.basic.domain.voucher.entity.Voucher;
import org.programmers.springboot.basic.domain.voucher.entity.VoucherType;
import org.programmers.springboot.basic.domain.voucher.service.validate.VoucherValidationStrategy;
import org.programmers.springboot.basic.util.generator.DateTimeGenerator;
import org.programmers.springboot.basic.util.generator.UUIDGenerator;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VoucherMapper {

    VoucherMapper INSTANCE = Mappers.getMapper(VoucherMapper.class);

    @Mapping(target = "voucherId", expression = "java(mapToUUID(voucherId))")
    @Mapping(target = "voucherType", expression = "java(mapToVoucherType(voucherType))")
    @Mapping(target = "discount", expression = "java(mapToLong(discount))")
    @Mapping(target = "createdAt", expression = "java(mapToDateTime(createdAt))")
    VoucherRequestDto mapToRequestDtoWithAllArgs(String voucherId, String voucherType, String discount, String createdAt);

    @Mapping(target = "voucherId", expression = "java(mapToUUID(requestDto.getVoucherId()))")
    @Mapping(target = "voucherType", expression = "java(mapToVoucherType(requestDto.getVoucherType()))")
    @Mapping(target = "discount", expression = "java(mapToLong(requestDto.getDiscount()))")
    @Mapping(target = "createdAt", expression = "java(mapToDateTime(requestDto.getCreatedAt()))")
    VoucherRequestDto mapToRequestDtoWithControllerDto(VoucherControllerRequestDto requestDto);

    @Mapping(target = "voucherType", source = "voucherType")
    @Mapping(target = "discount", source = "discount")
    VoucherRequestDto mapToRequestDto(VoucherType voucherType, Long discount);

    @Mapping(target = "voucherId", expression = "java(mapToUUID(voucherId))")
    @Mapping(target = "discount", source = "discount")
    VoucherRequestDto mapToRequestDtoWithIdNDiscount(String voucherId, Long discount);

    @Mapping(target = "voucherId", expression = "java(mapToUUID(voucherId))")
    VoucherRequestDto mapToRequestDtoWithUUID(String voucherId);

    @Mapping(target = "voucherId", expression = "java(generateUUID(uuidGenerator))")
    @Mapping(target = "createdAt", expression = "java(generateDateTime(dateTimeGenerator))")
    Voucher mapToEntityWithGenerator(VoucherRequestDto requestDto, @Context UUIDGenerator uuidGenerator,
                                     @Context DateTimeGenerator dateTimeGenerator,
                                     @Context Map<VoucherType, VoucherValidationStrategy> validationStrategyMap);

    VoucherResponseDto mapToResponseDto(Voucher voucher);

    Voucher mapToEntity(VoucherRequestDto voucherRequestDto);

    default UUID mapToUUID(String voucherId) {
        return voucherId != null ? UUID.fromString(voucherId) : null;
    }

    default LocalDateTime mapToDateTime(String createdAt) {
        return createdAt != null ? LocalDateTime.parse(createdAt) : null;
    }

    default Long mapToLong(String discount) {
        return discount != null ? Long.parseLong(discount) : null;
    }

    default VoucherType mapToVoucherType(String voucherType) {
        return voucherType != null ? VoucherType.valueOf(voucherType) : null;
    }

    default UUID generateUUID(@Context UUIDGenerator uuidGenerator) {
        return uuidGenerator.generateUUID();
    }

    default LocalDateTime generateDateTime(@Context DateTimeGenerator dateTimeGenerator) {
        return dateTimeGenerator.generateDateTime();
    }

    default VoucherValidationStrategy generateValidator(VoucherType voucherType, @Context Map<VoucherType, VoucherValidationStrategy> validationStrategyMap) {
        return validationStrategyMap.get(voucherType);
    }

    @AfterMapping
    default void setValidationStrategy(@MappingTarget Voucher voucher,
                                       VoucherRequestDto requestDto,
                                       @Context Map<VoucherType, VoucherValidationStrategy> validationStrategyMap) {
        VoucherType voucherType = requestDto.getVoucherType();
        VoucherValidationStrategy strategy = validationStrategyMap.get(voucherType);
        voucher.setValidationStrategy(strategy);
    }
}
