package com.prgrms.wallet.service.dto;

import com.prgrms.wallet.model.Wallet;

public record WalletServiceResponse(String voucherId,
                                    String customerId,
                                    boolean deleted) {

    public WalletServiceResponse(Wallet wallet) {
        this(wallet.getVoucherId(), wallet.getCustomerId(), wallet.isDeleted());
    }

    public boolean isDeleted() {
        return deleted;
    }

}
