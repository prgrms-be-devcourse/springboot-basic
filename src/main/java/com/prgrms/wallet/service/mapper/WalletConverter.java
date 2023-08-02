package com.prgrms.wallet.service.mapper;

import com.prgrms.wallet.model.Wallet;
import com.prgrms.wallet.service.dto.WalletServiceRequest;
import org.springframework.stereotype.Component;

@Component
public class WalletConverter {

    private WalletConverter() { }

    public Wallet convertWallet(String id, WalletServiceRequest walletServiceRequest) {
        String customerId = walletServiceRequest.customerId();
        String voucherId = walletServiceRequest.voucherId();

        return new Wallet(id, customerId, voucherId);
    }
}
