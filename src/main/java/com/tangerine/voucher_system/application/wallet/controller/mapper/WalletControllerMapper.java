package com.tangerine.voucher_system.application.wallet.controller.mapper;

import com.tangerine.voucher_system.application.global.generator.IdGenerator;
import com.tangerine.voucher_system.application.wallet.controller.dto.CreateWalletRequest;
import com.tangerine.voucher_system.application.wallet.controller.dto.UpdateWalletRequest;
import com.tangerine.voucher_system.application.wallet.service.dto.WalletParam;
import org.springframework.stereotype.Component;

@Component
public class WalletControllerMapper {

    private final IdGenerator idGenerator;

    public WalletControllerMapper(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    public WalletParam requestToParam(CreateWalletRequest request) {
        return new WalletParam(
                idGenerator.getUuid(),
                request.voucherId(),
                request.customerId()
        );
    }

    public WalletParam requestToParam(UpdateWalletRequest request) {
        return new WalletParam(
                request.walletId(),
                request.voucherId(),
                request.customerId()
        );
    }

}
