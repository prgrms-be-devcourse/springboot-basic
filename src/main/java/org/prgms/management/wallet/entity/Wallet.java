package org.prgms.management.wallet.entity;

import org.prgms.management.voucher.entity.Voucher;

import java.util.List;
import java.util.UUID;

public class Wallet {
    private final UUID walletId;
    private final UUID customerId;

    private final List<Voucher> vouchers;

    public Wallet(UUID walletId, UUID customerId, List<Voucher> vouchers) {
        this.walletId = walletId;
        this.customerId = customerId;
        this.vouchers = vouchers;
    }

    public UUID getWalletId() {
        return walletId;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public List<Voucher> getVouchers() {
        return vouchers;
    }
}
