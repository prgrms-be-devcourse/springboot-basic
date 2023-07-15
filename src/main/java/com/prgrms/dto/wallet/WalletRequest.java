package com.prgrms.dto.wallet;

public class WalletRequest {
    private final int customerId;
    private final int voucherId;

    public WalletRequest(int customerId, int voucherId) {
        this.customerId = customerId;
        this.voucherId = voucherId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getVoucherId() {
        return voucherId;
    }
}
