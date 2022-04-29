package com.example.voucherproject.wallet.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

/*
* user and voucher wallet
*/
@Getter
@ToString
@AllArgsConstructor
public class Wallet {
    private final UUID id;
    private final UUID userId;
    private final UUID voucherId;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static Wallet create(UUID userId, UUID voucherId){
        return new Wallet(UUID.randomUUID(),userId, voucherId,
                LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS),
                LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
    }
}
