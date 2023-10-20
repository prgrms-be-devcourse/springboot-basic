package com.programmers.springbootbasic.domain.wallet.entity;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class Wallet {
    private final String email;
    private final UUID voucherId;
}
