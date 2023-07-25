package com.wonu606.vouchermanager.service.voucherwallet.param;

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
