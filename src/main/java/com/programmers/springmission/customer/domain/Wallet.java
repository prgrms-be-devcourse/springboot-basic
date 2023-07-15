package com.programmers.springmission.customer.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class Wallet {

    private final UUID customerId;
    private final UUID voucherId;
    private final String voucherPolicy;
    private final long voucherAmount;
}
