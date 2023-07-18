package com.wonu606.vouchermanager.service.voucherwallet.param;

import com.wonu606.vouchermanager.domain.customer.email.Email;
import com.wonu606.vouchermanager.domain.voucherwallet.VoucherWallet;
import java.util.UUID;

public class WalletAssignParam {

    private final UUID voucherId;
    private final String emailAddress;

    public WalletAssignParam(UUID voucherId, String emailAddress) {
        this.voucherId = voucherId;
        this.emailAddress = emailAddress;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public VoucherWallet toEntity() {
        return new VoucherWallet(getVoucherId(), new Email(getEmailAddress()));
    }
}
