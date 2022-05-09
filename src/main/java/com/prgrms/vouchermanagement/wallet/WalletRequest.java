package com.prgrms.vouchermanagement.wallet;

public class WalletRequest {

    private Long customerId;
    private Long voucherId;

    public WalletRequest(Long customerId, Long voucherId) {
        this.customerId = customerId;
        this.voucherId = voucherId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public Long getVoucherId() {
        return voucherId;
    }
}
