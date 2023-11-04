package org.programmers.springboot.basic.domain.wallet.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.programmers.springboot.basic.domain.customer.entity.vo.Email;
import org.programmers.springboot.basic.domain.wallet.dto.WalletRequestDto;
import org.programmers.springboot.basic.domain.wallet.dto.WalletResponseDto;
import org.programmers.springboot.basic.domain.wallet.entity.Wallet;

import java.util.UUID;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WalletMapper {

    WalletMapper INSTANCE = Mappers.getMapper(WalletMapper.class);

    @Mapping(target = "voucherId", expression = "java(mapToUUID(voucherId))")
    @Mapping(target = "email", expression = "java(mapToEmail(email))")
    WalletRequestDto mapToRequestDto(String voucherId, String email);

    @Mapping(target = "voucherId", source = "voucherId")
    @Mapping(target = "email", source = "email")
    Wallet mapToEntity(UUID voucherId, Email email);

    @Mapping(target = "email", source = "email")
    WalletRequestDto mapToRequestDtoWithEmail(String email);

    @Mapping(target = "voucherId", expression = "java(mapToUUID(voucherId))")
    WalletRequestDto mapToRequestDtoWithUUID(String voucherId);

    Wallet mapToEntity(WalletRequestDto walletRequestDto);

    WalletResponseDto mapToResponseDto(Wallet wallet);

    default UUID mapToUUID(String value) {
        return UUID.fromString(value);
    }

    default Email mapToEmail(String value) {
        return Email.valueOf(value);
    }
}
