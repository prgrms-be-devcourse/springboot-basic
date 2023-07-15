package com.prgrms.dto.wallet;

import com.prgrms.model.KeyGenerator;
import com.prgrms.model.wallet.Wallet;
import org.springframework.stereotype.Component;

@Component
public class WalletConverter {

    private final KeyGenerator keyGenerator;

    public WalletConverter(KeyGenerator keyGenerator) {
        this.keyGenerator = keyGenerator;
    }

    public Wallet convertWallet(WalletRequest walletRequest) {
        int id = keyGenerator.make();
        int customerId = walletRequest.getCustomerId();
        int voucherId = walletRequest.getVoucherId();

        return new Wallet(id, customerId, voucherId);
    }
}
