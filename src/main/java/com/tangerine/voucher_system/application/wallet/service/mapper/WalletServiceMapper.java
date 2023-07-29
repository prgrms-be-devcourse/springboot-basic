package com.tangerine.voucher_system.application.wallet.service.mapper;

import com.tangerine.voucher_system.application.wallet.model.Wallet;
import com.tangerine.voucher_system.application.wallet.service.dto.WalletParam;
import com.tangerine.voucher_system.application.wallet.service.dto.WalletResult;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WalletServiceMapper {

    Wallet paramToDomain(WalletParam param);

    WalletResult domainToResult(Wallet domain);

}
