package com.tangerine.voucher_system.application.wallet.service.mapper;

import com.tangerine.voucher_system.application.wallet.model.Wallet;
import com.tangerine.voucher_system.application.wallet.service.dto.WalletParam;
import com.tangerine.voucher_system.application.wallet.service.dto.WalletResult;
import org.springframework.stereotype.Component;

@Component
public class WalletServiceMapper {

    public Wallet paramToDomain(WalletParam param) {
        return new Wallet(
                param.walletId(),
                param.voucherId(),
                param.customerId()
        );
    }

    public WalletResult domainToResult(Wallet domain) {
        return new WalletResult(
                domain.walletId(),
                domain.voucherId(),
                domain.customerId()
        );
    }

}
