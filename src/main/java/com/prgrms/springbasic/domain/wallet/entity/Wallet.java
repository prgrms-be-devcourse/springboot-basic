package com.prgrms.springbasic.domain.wallet.entity;

import java.util.UUID;

public class Wallet {
    private final UUID wallet_id;
    private final UUID customer_id;
    private final UUID voucher_id;

    public Wallet(UUID wallet_id, UUID customer_id, UUID voucher_id) {
        this.wallet_id = wallet_id;
        this.customer_id = customer_id;
        this.voucher_id = voucher_id;
    }

    public UUID getWallet_id() {
        return wallet_id;
    }

    public UUID getCustomer_id() {
        return customer_id;
    }

    public UUID getVoucher_id() {
        return voucher_id;
    }
}
