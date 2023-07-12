package com.programmers.springbootbasic.wallet.dto;

import java.util.UUID;

public record WalletDto(UUID voucherId, UUID customerId) {

}