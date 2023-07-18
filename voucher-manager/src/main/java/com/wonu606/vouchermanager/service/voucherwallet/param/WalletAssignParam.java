package com.wonu606.vouchermanager.service.voucherwallet.param;

import com.wonu606.vouchermanager.domain.customer.email.Email;
import com.wonu606.vouchermanager.domain.voucherwallet.VoucherWallet;
import java.util.UUID;

public class WalletAssignParam {

    private final UUID voucherId;
    private final String email;

    public WalletAssignParam(UUID voucherId, String email) {
        this.voucherId = voucherId;
        this.email = email;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public String getEmail() {
        return email;
    }

    public VoucherWallet toEntity() {
        return new VoucherWallet(getVoucherId(), new Email(getEmail()));
    }
}
