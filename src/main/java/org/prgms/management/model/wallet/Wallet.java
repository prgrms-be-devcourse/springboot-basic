package org.prgms.management.model.wallet;

import org.prgms.management.model.voucher.Voucher;

import java.time.LocalDateTime;
import java.util.UUID;

public class Wallet {
    private final UUID walletId;
    private final UUID customerId;

    private final Voucher voucher;

    private final LocalDateTime createdAt;

    private Wallet(UUID walletId, UUID customerId, Voucher voucher, LocalDateTime createdAt) {
        this.walletId = walletId;
        this.customerId = customerId;
        this.voucher = voucher;
        this.createdAt = createdAt;
    }

    public static Wallet getWallet(UUID walletId, UUID customerId, Voucher voucher, LocalDateTime createdAt) {
        if (!validate(voucher)) {
            return null;
        }

        return new Wallet(walletId, customerId, voucher, createdAt);
    }

    private static boolean validate(Voucher voucher) {
        return voucher != null;
    }

    public UUID getWalletId() {
        return walletId;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public Voucher getVoucher() {
        return voucher;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
