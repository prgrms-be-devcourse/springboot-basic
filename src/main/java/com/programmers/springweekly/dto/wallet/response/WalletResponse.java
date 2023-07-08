package com.programmers.springweekly.dto.wallet.response;

import com.programmers.springweekly.domain.wallet.Wallet;
import lombok.Getter;

import java.util.UUID;

@Getter
public class WalletResponse {
    private UUID walletId;
    private UUID customerId;
    private UUID voucherId;

    public WalletResponse(Wallet wallet) {
        this.walletId = wallet.getWalletId();
        this.customerId = wallet.getCustomerId();
        this.voucherId = wallet.getVoucherId();
    }
}
