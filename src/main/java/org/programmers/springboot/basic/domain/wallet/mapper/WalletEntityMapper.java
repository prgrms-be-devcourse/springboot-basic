package org.programmers.springboot.basic.domain.wallet.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.programmers.springboot.basic.domain.wallet.dto.WalletRequestDto;
import org.programmers.springboot.basic.domain.wallet.dto.WalletResponseDto;
import org.programmers.springboot.basic.domain.wallet.entity.Wallet;

import java.util.UUID;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WalletEntityMapper {

    WalletEntityMapper INSTANCE = Mappers.getMapper(WalletEntityMapper.class);

    @Mapping(target = "email", source = "email")
    @Mapping(target = "voucherId", source = "voucherId")
    WalletRequestDto mapToDto(String email, String voucherId);

    Wallet mapToEntity(WalletRequestDto walletRequestDto);

    @Mapping(target = "email", source = "email")
    WalletRequestDto mapToDtoWithEmail(String email);

    @Mapping(target = "voucherId", source = "input")
    WalletRequestDto mapToDtoWithUUID(String input);

    WalletResponseDto mapToResponseDto(Wallet wallet);

    default UUID map(String value) {
        return UUID.fromString(value);
    }
}
