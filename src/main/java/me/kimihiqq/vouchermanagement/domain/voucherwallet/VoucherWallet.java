package me.kimihiqq.vouchermanagement.domain.voucherwallet;

import java.util.UUID;


public class VoucherWallet {
    private UUID customerId;

    public VoucherWallet(UUID customerId) {
        this.customerId = customerId;
    }

    public UUID getCustomerId() {
        return customerId;
    }
}