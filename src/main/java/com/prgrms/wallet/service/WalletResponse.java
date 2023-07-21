package com.prgrms.wallet.service;

import com.prgrms.wallet.model.Wallet;

public record WalletResponse(int voucherId,
                             int customerId,
                             boolean deleted) {

    public WalletResponse(Wallet wallet) {
        this(wallet.getVoucherId(), wallet.getCustomerId(), wallet.isDeleted());
    }

    public boolean isDeleted() {
        return deleted;
    }

}
