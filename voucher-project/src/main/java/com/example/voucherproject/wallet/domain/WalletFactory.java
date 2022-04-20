package com.example.voucherproject.wallet.domain;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class WalletFactory {
    public static Wallet create(UUID userId, UUID voucherId){
        return new Wallet(UUID.randomUUID(),userId, voucherId,
                LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
    }
}
