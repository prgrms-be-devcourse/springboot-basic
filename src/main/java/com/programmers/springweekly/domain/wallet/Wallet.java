package com.programmers.springweekly.domain.wallet;

import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Wallet {

    private final UUID customerId;
    private final UUID voucherId;
}
