package com.wonu606.vouchermanager.service.customer.param;

import java.util.UUID;

public class WalletRegisterParam {

    private final UUID voucherId;
    private final String customerId;

    public WalletRegisterParam(UUID voucherId, String customerId) {
        this.voucherId = voucherId;
        this.customerId = customerId;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public String getCustomerId() {
        return customerId;
    }
}
