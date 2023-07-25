package com.wonu606.vouchermanager.domain.CustomerVoucherWallet;

import java.util.UUID;

public class CustomerVoucherWalletDto {
    private final UUID voucherId;
    private final String emailAddress;

    public CustomerVoucherWalletDto(UUID voucherId, String emailAddress) {
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
