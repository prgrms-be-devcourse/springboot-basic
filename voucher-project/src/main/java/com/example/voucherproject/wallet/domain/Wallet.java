package com.example.voucherproject.wallet.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

/*
* user and voucher wallet
*/
@Getter
@ToString
@RequiredArgsConstructor
public class Wallet {
    private final UUID id;
    private final UUID userId;
    private final UUID voucherId;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
