package com.wonu606.vouchermanager.service.voucherwallet.param;

import com.wonu606.vouchermanager.domain.customer.email.Email;
import com.wonu606.vouchermanager.domain.voucherwallet.VoucherWallet;
import java.util.UUID;

public class WalletAssignParam {

    private final UUID voucherId;

    public WalletAssignParam(UUID voucherId) {
        this.voucherId = voucherId;
    }

    public UUID getVoucherId() {
        return voucherId;
    }
}
