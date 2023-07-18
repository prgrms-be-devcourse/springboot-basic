package com.wonu606.vouchermanager.domain.voucherwallet;

import java.util.UUID;

public class VoucherWalletResultSet {
    private final UUID voucherId;
    private final String emailAddress;

    public VoucherWalletResultSet(UUID voucherId, String emailAddress) {
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
