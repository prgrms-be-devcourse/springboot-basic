package com.programmers.springweekly.dto.wallet.response;

import com.programmers.springweekly.domain.wallet.Wallet;
import java.util.UUID;
import lombok.Getter;

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
