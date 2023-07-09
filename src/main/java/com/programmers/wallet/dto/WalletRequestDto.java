package com.programmers.wallet.dto;

import java.util.UUID;

public record WalletRequestDto(UUID voucherId, UUID customerId) {

}
