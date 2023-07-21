package com.prgrms.wallet.service;

import com.prgrms.wallet.dto.WalletRequest;
import com.prgrms.wallet.model.Wallet;
import org.springframework.stereotype.Component;

@Component
public class WalletConverter {

    private WalletConverter() { }

    public Wallet convertWallet(int id, WalletRequest walletRequest) {
        int customerId = walletRequest.customerId();
        int voucherId = walletRequest.voucherId();

        return new Wallet(id, customerId, voucherId);
    }
}
