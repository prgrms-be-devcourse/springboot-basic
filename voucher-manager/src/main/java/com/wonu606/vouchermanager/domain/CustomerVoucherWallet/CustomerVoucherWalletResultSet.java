package com.wonu606.vouchermanager.domain.CustomerVoucherWallet;

import java.util.UUID;

public class CustomerVoucherWalletResultSet {
    private final UUID voucherId;
    private final String emailAddress;

    public CustomerVoucherWalletResultSet(UUID voucherId, String emailAddress) {
        this.voucherId = voucherId;
        this.emailAddress = emailAddress;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public String getEmailAddress() {
        return emailAddress;
    }
}
