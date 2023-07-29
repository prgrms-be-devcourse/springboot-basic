package com.tangerine.voucher_system.application.wallet.service.mapper;

import com.tangerine.voucher_system.application.wallet.model.Wallet;
import com.tangerine.voucher_system.application.wallet.service.dto.WalletParam;
import com.tangerine.voucher_system.application.wallet.service.dto.WalletResult;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface WalletServiceMapper {

    WalletServiceMapper INSTANCE = Mappers.getMapper(WalletServiceMapper.class);

    Wallet paramToDomain(WalletParam param);

    WalletResult domainToResult(Wallet domain);

}
